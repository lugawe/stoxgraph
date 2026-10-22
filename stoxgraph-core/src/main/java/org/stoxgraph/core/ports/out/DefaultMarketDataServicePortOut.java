package org.stoxgraph.core.ports.out;

import java.util.Objects;
import java.util.concurrent.Flow;

import org.stoxgraph.core.domain.marketdata.CandleQuery;
import org.stoxgraph.core.domain.marketdata.CandleSeries;
import org.stoxgraph.core.domain.marketdata.Quote;
import org.stoxgraph.core.domain.marketdata.Symbol;
import org.stoxgraph.core.ports.out.provider.MarketDataProvider;
import org.stoxgraph.core.service.logging.Logger;

public final class DefaultMarketDataServicePortOut implements MarketDataServicePortOut {

    private final Logger logger;
    private final MarketDataProvider marketDataProvider;

    public DefaultMarketDataServicePortOut(Logger logger, MarketDataProvider marketDataProvider) {
        this.logger = Objects.requireNonNull(logger);
        this.marketDataProvider = Objects.requireNonNull(marketDataProvider);
    }

    @Override
    public Quote getQuote(Symbol symbol) {

        logger.debug("getQuote {}", symbol);

        return marketDataProvider.getQuote(symbol);
    }

    @Override
    public CandleSeries getCandles(Symbol symbol, CandleQuery query) {

        logger.debug("getCandles {} {}", symbol, query);

        return marketDataProvider.getCandles(symbol, query);
    }

    @Override
    public Flow.Publisher<Quote> streamQuotes(Symbol symbol) {

        logger.debug("streamQuotes {}", symbol);

        return marketDataProvider.streamQuotes(symbol);
    }
}
