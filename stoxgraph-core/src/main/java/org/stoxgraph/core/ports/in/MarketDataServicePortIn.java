package org.stoxgraph.core.ports.in;

import java.util.concurrent.Flow;

import org.stoxgraph.core.domain.marketdata.CandleQuery;
import org.stoxgraph.core.domain.marketdata.CandleSeries;
import org.stoxgraph.core.domain.marketdata.Quote;
import org.stoxgraph.core.domain.marketdata.Symbol;
import org.stoxgraph.core.domain.money.Currency;

public interface MarketDataServicePortIn {

    Quote getQuote(Symbol symbol, Currency currency);

    CandleSeries getCandles(Symbol symbol, CandleQuery query, Currency currency);

    Flow.Publisher<Quote> streamQuotes(Symbol symbol, Currency currency);
}
