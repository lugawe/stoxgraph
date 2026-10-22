package org.stoxgraph.server.adapters.out.provider.twelvedata;

import java.math.BigDecimal;
import java.util.Objects;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import com.twelvedata.client.ws.PriceEvent;
import io.smallrye.mutiny.Multi;
import org.stoxgraph.core.domain.marketdata.CandleQuery;
import org.stoxgraph.core.domain.marketdata.CandleSeries;
import org.stoxgraph.core.domain.marketdata.Quote;
import org.stoxgraph.core.domain.marketdata.Symbol;
import org.stoxgraph.core.ports.out.MarketDataServicePortOut;

@ApplicationScoped
public class TwelveDataMarketService implements MarketDataServicePortOut {

    private final TwelveDataClient client;

    @Inject
    public TwelveDataMarketService(TwelveDataClient client) {
        this.client = client;
    }

    @Override
    public CandleSeries getCandles(Symbol symbol, CandleQuery query) {
        return query.interval().isIntraday()
                ? client.getIntradayCandleSeries(symbol, query)
                : client.getCandleSeries(symbol, query);
    }

    @Override
    public Quote getQuote(Symbol symbol) {
        return client.getQuote(symbol);
    }

    @Override
    public Multi<Quote> streamQuotes(Symbol symbol) {
        Quote quote = client.getQuote(symbol);
        return client.streamPrices(symbol).onItem().scan(() -> quote, TwelveDataMarketService::applyTick);
    }

    private static Quote applyTick(Quote quote, PriceEvent tick) {
        long volume = Objects.requireNonNullElse(tick.getDayVolume(), quote.volume());
        return quote.updatePrice(tick.getTimestamp(), BigDecimal.valueOf(tick.getPrice()), volume);
    }
}
