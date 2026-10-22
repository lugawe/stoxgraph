package org.stoxgraph.server.adapters.in.api.rest.service;

import java.time.LocalDate;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.stoxgraph.core.domain.marketdata.CandleQuery;
import org.stoxgraph.core.domain.marketdata.CandleSeries;
import org.stoxgraph.core.domain.marketdata.Interval;
import org.stoxgraph.core.domain.marketdata.Quote;
import org.stoxgraph.core.domain.marketdata.Symbol;
import org.stoxgraph.core.domain.money.Currency;
import org.stoxgraph.core.ports.in.MarketDataServicePortIn;
import org.stoxgraph.server.adapters.in.api.json.JsonCandleSeries;
import org.stoxgraph.server.adapters.in.api.json.JsonQuote;
import org.stoxgraph.server.adapters.in.api.json.converter.JsonConverter;

import io.smallrye.mutiny.Multi;

@ApplicationScoped
public class RestInstrumentService {

    private final JsonConverter jsonConverter;
    private final MarketDataServicePortIn marketDataServicePortIn;

    @Inject
    public RestInstrumentService(JsonConverter jsonConverter, MarketDataServicePortIn marketDataServicePortIn) {
        this.jsonConverter = jsonConverter;
        this.marketDataServicePortIn = marketDataServicePortIn;
    }

    public JsonCandleSeries getCandles(
            String symbol, Interval interval, LocalDate start, LocalDate end, long limit, Currency currency) {

        Symbol s = new Symbol(symbol);
        CandleQuery candleQuery = new CandleQuery(interval, start, end, limit);

        CandleSeries result = marketDataServicePortIn.getCandles(s, candleQuery, currency);

        return jsonConverter.toJsonCandleSeries(result);
    }

    public JsonQuote getQuote(String symbol, Currency currency) {

        Symbol s = new Symbol(symbol);

        Quote result = marketDataServicePortIn.getQuote(s, currency);

        return jsonConverter.toJsonQuote(result);
    }

    public Multi<JsonQuote> streamQuotes(String symbol, Currency currency) {

        Symbol s = new Symbol(symbol);

        Multi<Quote> result = Multi.createFrom().publisher(marketDataServicePortIn.streamQuotes(s, currency));

        return result.map(jsonConverter::toJsonQuote);
    }
}
