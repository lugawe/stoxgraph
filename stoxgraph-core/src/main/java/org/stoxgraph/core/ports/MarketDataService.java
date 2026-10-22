package org.stoxgraph.core.ports;

import java.util.concurrent.Flow;

import org.stoxgraph.core.domain.marketdata.CandleQuery;
import org.stoxgraph.core.domain.marketdata.CandleSeries;
import org.stoxgraph.core.domain.marketdata.Quote;
import org.stoxgraph.core.domain.marketdata.Symbol;

/**
 * Market data in the native currency of the stock.
 */
public interface MarketDataService {

    Quote getQuote(Symbol symbol);

    CandleSeries getCandles(Symbol symbol, CandleQuery query);

    Flow.Publisher<Quote> streamQuotes(Symbol symbol);
}
