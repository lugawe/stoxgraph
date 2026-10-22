package org.stoxgraph.server.adapters.in.api.rest;

import java.time.LocalDate;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import io.smallrye.common.annotation.Blocking;
import io.smallrye.common.annotation.RunOnVirtualThread;
import io.smallrye.mutiny.Multi;
import org.jboss.resteasy.reactive.RestStreamElementType;
import org.stoxgraph.core.domain.marketdata.Interval;
import org.stoxgraph.core.domain.money.Currency;
import org.stoxgraph.server.adapters.in.api.json.JsonCandleSeries;
import org.stoxgraph.server.adapters.in.api.json.JsonQuote;
import org.stoxgraph.server.adapters.in.api.rest.service.RestInstrumentService;

@RunOnVirtualThread
@Path("/instruments")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class InstrumentResource {

    private final RestInstrumentService restInstrumentService;

    @Inject
    public InstrumentResource(RestInstrumentService restInstrumentService) {
        this.restInstrumentService = restInstrumentService;
    }

    @GET
    @Path("/{symbol}/candles")
    public JsonCandleSeries getCandles(
            @PathParam("symbol") String symbol,
            @QueryParam("interval") @DefaultValue("1day") Interval interval,
            @QueryParam("start") LocalDate start,
            @QueryParam("end") LocalDate end,
            @QueryParam("limit") @DefaultValue("30") long limit,
            @QueryParam("currency") @DefaultValue("EUR") Currency currency) {

        return restInstrumentService.getCandles(symbol, interval, start, end, limit, currency);
    }

    @GET
    @Path("/{symbol}/quote")
    public JsonQuote getQuote(
            @PathParam("symbol") String symbol, @QueryParam("currency") @DefaultValue("EUR") Currency currency) {

        return restInstrumentService.getQuote(symbol, currency);
    }

    @Blocking
    @GET
    @Path("/{symbol}/quote/stream")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @RestStreamElementType(MediaType.APPLICATION_JSON)
    public Multi<JsonQuote> streamQuotes(
            @PathParam("symbol") String symbol, @QueryParam("currency") @DefaultValue("EUR") Currency currency) {

        return restInstrumentService.streamQuotes(symbol, currency);
    }
}
