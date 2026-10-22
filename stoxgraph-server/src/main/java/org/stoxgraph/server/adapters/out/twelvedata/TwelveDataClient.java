package org.stoxgraph.server.adapters.out.twelvedata;

import java.math.BigDecimal;
import java.util.*;

import jakarta.annotation.PreDestroy;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import com.twelvedata.client.ApiClient;
import com.twelvedata.client.Twelvedata;
import com.twelvedata.client.api.CurrenciesApi;
import com.twelvedata.client.api.CurrenciesApi.APIGetExchangeRateRequest;
import com.twelvedata.client.api.MarketDataApi;
import com.twelvedata.client.api.MarketDataApi.APIGetQuoteRequest;
import com.twelvedata.client.api.MarketDataApi.APIGetTimeSeriesRequest;
import com.twelvedata.client.model.GetExchangeRate200Response;
import com.twelvedata.client.model.GetQuote200Response;
import com.twelvedata.client.model.GetTimeSeries200Response;
import com.twelvedata.client.model.TimeSeriesItem;
import com.twelvedata.client.ws.PriceEvent;
import com.twelvedata.client.ws.TwelvedataWebSocketClient;
import com.twelvedata.client.ws.TwelvedataWebSocketOptions;
import io.quarkus.cache.CacheResult;
import io.smallrye.mutiny.Multi;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stoxgraph.core.domain.marketdata.Candle;
import org.stoxgraph.core.domain.marketdata.CandleQuery;
import org.stoxgraph.core.domain.marketdata.CandleSeries;
import org.stoxgraph.core.domain.marketdata.Quote;
import org.stoxgraph.core.domain.marketdata.Symbol;
import org.stoxgraph.core.domain.money.Currency;

@Singleton
public class TwelveDataClient {

    private static final Logger log = LoggerFactory.getLogger(TwelveDataClient.class);

    private final MarketDataApi api;
    private final CurrenciesApi currencies;
    private final TwelveDataPriceStream priceStream;

    @Inject
    public TwelveDataClient(@ConfigProperty(name = "twelvedata.api-key") String apiKey) {
        ApiClient client = Twelvedata.newApiClient(apiKey);
        this.api = new MarketDataApi(client);
        this.currencies = new CurrenciesApi(client);
        this.priceStream = new TwelveDataPriceStream(new TwelvedataWebSocketClient(
                TwelvedataWebSocketOptions.builder().apiKey(apiKey).build()));
    }

    @PreDestroy
    public void shutdown() {
        priceStream.disconnect();
    }

    @CacheResult(cacheName = "exchange-rates")
    public BigDecimal getExchangeRate(Currency from, Currency to) {
        log.info("Twelve Data API: Request exchange rate {}/{}", from, to);
        GetExchangeRate200Response r = Utils.call(() -> currencies.getExchangeRate(
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
        GetQuote200Response r = Utils.call(() -> api.getQuote(
                APIGetQuoteRequest.newBuilder().symbol(symbol.value()).build()));
        return toQuote(r);
    }

    public Multi<PriceEvent> streamPrices(Symbol symbol) {
        return priceStream.prices(symbol);
    }

    private CandleSeries fetchCandleSeries(Symbol symbol, CandleQuery query) {
        log.info("Twelve Data API: Request candles {} {}", symbol, query);
        GetTimeSeries200Response r = Utils.call(() -> api.getTimeSeries(APIGetTimeSeriesRequest.newBuilder()
                .symbol(symbol.value())
                .interval(Utils.toIntervalEnum(query.interval()))
                .startDate(Objects.toString(query.start(), null))
                .endDate(query.end() != null ? query.end().plusDays(1).toString() : null) // end_date is exclusive
                .outputsize(query.limit())
                .timezone("UTC")
                .build()));

        List<Candle> candles =
                r.getValues().stream().map(TwelveDataClient::toCandle).toList();
        Currency currency = Utils.currency(r.getMeta().getCurrency());

        return new CandleSeries(symbol, query.interval(), currency, candles);
    }

    private static Candle toCandle(TimeSeriesItem v) {
        return new Candle(
                Utils.timestamp(v.getDatetime()),
                Utils.decimal(v.getOpen()),
                Utils.decimal(v.getHigh()),
                Utils.decimal(v.getLow()),
                Utils.decimal(v.getClose()),
                Utils.number(v.getVolume()));
    }

    private static Quote toQuote(GetQuote200Response r) {
        return new Quote(
                Objects.requireNonNullElse(r.getLastQuoteAt(), r.getTimestamp()),
                new Symbol(r.getSymbol()),
                r.getName(),
                r.getExchange(),
                Utils.currency(r.getCurrency()),
                Utils.decimal(r.getClose()),
                Utils.decimal(r.getOpen()),
                Utils.decimal(r.getHigh()),
                Utils.decimal(r.getLow()),
                Utils.decimal(r.getPreviousClose()),
                Utils.decimal(r.getChange()),
                Utils.decimal(r.getPercentChange()),
                Utils.number(r.getVolume()),
                r.getIsMarketOpen());
    }
}
