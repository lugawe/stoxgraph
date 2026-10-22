package org.stoxgraph.server.adapters.in.api.mcp.service;

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

@ApplicationScoped
public class McpInstrumentService {

    private final JsonConverter jsonConverter;
    private final MarketDataServicePortIn marketDataServicePortIn;

    @Inject
    public McpInstrumentService(JsonConverter jsonConverter, MarketDataServicePortIn marketDataServicePortIn) {
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
}
