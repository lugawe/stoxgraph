package org.stoxgraph.core.ports.out;

import java.util.concurrent.Flow;

import org.stoxgraph.core.domain.marketdata.CandleQuery;
import org.stoxgraph.core.domain.marketdata.CandleSeries;
import org.stoxgraph.core.domain.marketdata.Quote;
import org.stoxgraph.core.domain.marketdata.Symbol;

public interface MarketDataServicePortOut {

    Quote getQuote(Symbol symbol);

    CandleSeries getCandles(Symbol symbol, CandleQuery query);

    Flow.Publisher<Quote> streamQuotes(Symbol symbol);
}
