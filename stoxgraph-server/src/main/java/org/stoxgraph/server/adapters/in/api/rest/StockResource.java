package org.stoxgraph.server.adapters.in.api.rest;

import java.time.LocalDate;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import io.smallrye.common.annotation.Blocking;
import io.smallrye.common.annotation.RunOnVirtualThread;
import io.smallrye.mutiny.Multi;
import org.jboss.resteasy.reactive.RestStreamElementType;
import org.stoxgraph.core.domain.marketdata.CandleQuery;
import org.stoxgraph.core.domain.marketdata.CandleSeries;
import org.stoxgraph.core.domain.marketdata.Interval;
import org.stoxgraph.core.domain.marketdata.Quote;
import org.stoxgraph.core.domain.marketdata.Symbol;
import org.stoxgraph.core.domain.money.Currency;
import org.stoxgraph.core.ports.in.MarketDataServicePortIn;

@RunOnVirtualThread
@Path("/stocks")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StockResource {

    private final MarketDataServicePortIn marketDataServicePortIn;

    @Inject
    public StockResource(MarketDataServicePortIn marketDataServicePortIn) {
        this.marketDataServicePortIn = marketDataServicePortIn;
    }

    @GET
    @Path("/{symbol}/candles")
    public CandleSeries getCandles(
            @PathParam("symbol") String symbol,
            @QueryParam("interval") @DefaultValue("1day") Interval interval,
            @QueryParam("start") LocalDate start,
            @QueryParam("end") LocalDate end,
            @QueryParam("limit") @DefaultValue("30") long limit,
            @QueryParam("currency") @DefaultValue("EUR") Currency currency) {

        Symbol s = new Symbol(symbol);
        CandleQuery candleQuery = new CandleQuery(interval, start, end, limit);
        return marketDataServicePortIn.getCandles(s, candleQuery, currency);
    }

    @GET
    @Path("/{symbol}/quote")
    public Quote getQuote(
            @PathParam("symbol") String symbol, @QueryParam("currency") @DefaultValue("EUR") Currency currency) {

        Symbol s = new Symbol(symbol);
        return marketDataServicePortIn.getQuote(s, currency);
    }

    @Blocking
    @GET
    @Path("/{symbol}/quote/stream")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @RestStreamElementType(MediaType.APPLICATION_JSON)
    public Multi<Quote> streamQuotes(
            @PathParam("symbol") String symbol, @QueryParam("currency") @DefaultValue("EUR") Currency currency) {

        Symbol s = new Symbol(symbol);
        return Multi.createFrom().publisher(marketDataServicePortIn.streamQuotes(s, currency));
    }
}
