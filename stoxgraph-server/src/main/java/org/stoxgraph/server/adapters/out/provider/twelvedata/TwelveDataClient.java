package org.stoxgraph.server.adapters.out.provider.twelvedata;

import java.math.BigDecimal;
import java.util.Objects;

import jakarta.annotation.PreDestroy;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import com.twelvedata.client.ApiClient;
import com.twelvedata.client.ApiException;
import com.twelvedata.client.Twelvedata;
import com.twelvedata.client.api.CurrenciesApi;
import com.twelvedata.client.api.CurrenciesApi.APIGetExchangeRateRequest;
import com.twelvedata.client.api.MarketDataApi;
import com.twelvedata.client.api.MarketDataApi.APIGetQuoteRequest;
import com.twelvedata.client.api.MarketDataApi.APIGetTimeSeriesRequest;
import com.twelvedata.client.errors.TwelvedataApiException;
import com.twelvedata.client.model.GetExchangeRate200Response;
import com.twelvedata.client.model.GetQuote200Response;
import com.twelvedata.client.model.GetTimeSeries200Response;
import com.twelvedata.client.ws.PriceEvent;
import com.twelvedata.client.ws.TwelvedataWebSocketClient;
import com.twelvedata.client.ws.TwelvedataWebSocketOptions;
import io.quarkus.cache.CacheResult;
import io.smallrye.mutiny.Multi;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stoxgraph.core.domain.marketdata.CandleQuery;
import org.stoxgraph.core.domain.marketdata.CandleSeries;
import org.stoxgraph.core.domain.marketdata.Quote;
import org.stoxgraph.core.domain.marketdata.Symbol;
import org.stoxgraph.core.domain.money.Currency;
import org.stoxgraph.core.exception.MarketDataException;
import org.stoxgraph.core.exception.UnknownSymbolException;
import org.stoxgraph.server.adapters.out.provider.twelvedata.converter.TwelveDataConverter;

@Singleton
public class TwelveDataClient {

    private static final Logger log = LoggerFactory.getLogger(TwelveDataClient.class);

    private final MarketDataApi api;
    private final CurrenciesApi currencies;
    private final TwelveDataPriceStream priceStream;
    private final TwelveDataConverter twelveDataConverter;

    @Inject
    public TwelveDataClient(
            @ConfigProperty(name = "twelvedata.api-key") String apiKey, TwelveDataConverter twelveDataConverter) {

        ApiClient client = Twelvedata.newApiClient(apiKey);
        this.api = new MarketDataApi(client);
        this.currencies = new CurrenciesApi(client);
        this.priceStream = new TwelveDataPriceStream(new TwelvedataWebSocketClient(
                TwelvedataWebSocketOptions.builder().apiKey(apiKey).build()));
        this.twelveDataConverter = twelveDataConverter;
    }

    @PreDestroy
    public void shutdown() {
        priceStream.disconnect();
    }

    @CacheResult(cacheName = "exchange-rates")
    public BigDecimal getExchangeRate(Currency from, Currency to) {
        log.info("Twelve Data API: Request exchange rate {}/{}", from, to);
        GetExchangeRate200Response r = call(() -> currencies.getExchangeRate(
                APIGetExchangeRateRequest.newBuilder().symbol(from + "/" + to).build()));
        return BigDecimal.valueOf(r.getRate());
    }

    @CacheResult(cacheName = "candles")
    public CandleSeries getCandleSeries(Symbol symbol, CandleQuery query) {
        return fetchCandleSeries(symbol, query);
    }

    @CacheResult(cacheName = "candles-intraday")
    public CandleSeries getIntradayCandleSeries(Symbol symbol, CandleQuery query) {
        return fetchCandleSeries(symbol, query);
    }

    @CacheResult(cacheName = "quotes")
    public Quote getQuote(Symbol symbol) {
        log.info("Twelve Data API: Request quote {}", symbol);
        GetQuote200Response r = call(() -> api.getQuote(
                APIGetQuoteRequest.newBuilder().symbol(symbol.value()).build()));
        return twelveDataConverter.toQuote(r);
    }

    public Multi<PriceEvent> streamPrices(Symbol symbol) {
        return priceStream.prices(symbol);
    }

    private CandleSeries fetchCandleSeries(Symbol symbol, CandleQuery query) {
        log.info("Twelve Data API: Request candles {} {}", symbol, query);
        GetTimeSeries200Response r = call(() -> api.getTimeSeries(APIGetTimeSeriesRequest.newBuilder()
                .symbol(symbol.value())
                .interval(twelveDataConverter.toIntervalEnum(query.interval()))
                .startDate(Objects.toString(query.start(), null))
                .endDate(query.end() != null ? query.end().plusDays(1).toString() : null) // end_date is exclusive
                .outputsize(query.limit())
                .timezone("UTC")
                .build()));
        return twelveDataConverter.toCandleSeries(symbol, query.interval(), r);
    }

    private interface ApiCall<T> {
        T call() throws ApiException;
    }

    private static <T> T call(ApiCall<T> call) {
        try {
            return call.call();
        } catch (TwelvedataApiException.NotFoundException e) {
            throw new UnknownSymbolException(e.getMessage(), e);
        } catch (TwelvedataApiException | ApiException e) {
            throw new MarketDataException("Twelve Data API request failed", e);
        }
    }
}
