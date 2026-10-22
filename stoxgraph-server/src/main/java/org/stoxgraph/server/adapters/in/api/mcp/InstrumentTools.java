package org.stoxgraph.server.adapters.in.api.mcp;

import java.time.LocalDate;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import io.quarkiverse.mcp.server.Tool;
import io.quarkiverse.mcp.server.ToolArg;
import io.quarkiverse.mcp.server.ToolCallException;
import org.stoxgraph.core.domain.marketdata.Interval;
import org.stoxgraph.core.domain.money.Currency;
import org.stoxgraph.core.exception.MarketDataException;
import org.stoxgraph.server.adapters.in.api.json.JsonCandleSeries;
import org.stoxgraph.server.adapters.in.api.json.JsonQuote;
import org.stoxgraph.server.adapters.in.api.mcp.service.McpInstrumentService;

@Singleton
public class InstrumentTools {

    private final McpInstrumentService mcpInstrumentService;

    @Inject
    public InstrumentTools(McpInstrumentService mcpInstrumentService) {
        this.mcpInstrumentService = mcpInstrumentService;
    }

    @Tool(
            name = "get_instrument_candles",
            description = "Returns the historical OHLC candles of an instrument (stock or ETF).",
            annotations =
                    @Tool.Annotations(
                            readOnlyHint = true,
                            destructiveHint = false,
                            idempotentHint = true,
                            openWorldHint = true))
    public JsonCandleSeries getInstrumentCandles(
            @ToolArg(description = "Ticker symbol of the instrument, e.g. a stock (AAPL) or an ETF (QQQ)")
                    String symbol,
            @ToolArg(description = "Length of one candle", defaultValue = "DAY_1") Interval interval,
            @ToolArg(description = "First day to include (ISO date, e.g. 2026-01-31)", required = false)
                    LocalDate start,
            @ToolArg(description = "Last day to include (ISO date, e.g. 2026-01-31)", required = false) LocalDate end,
            @ToolArg(description = "Maximum number of candles", defaultValue = "30") long limit,
            @ToolArg(description = "Currency of the prices (ISO 4217 code)", defaultValue = "EUR") Currency currency) {

        try {
            return mcpInstrumentService.getCandles(symbol, interval, start, end, limit, currency);
        } catch (MarketDataException e) {
            throw new ToolCallException(e.getMessage(), e);
        }
    }

    @Tool(
            name = "get_instrument_quote",
            description = "Returns the current quote of an instrument (stock or ETF).",
            annotations =
                    @Tool.Annotations(
                            readOnlyHint = true,
                            destructiveHint = false,
                            idempotentHint = true,
                            openWorldHint = true))
    public JsonQuote getInstrumentQuote(
            @ToolArg(description = "Ticker symbol of the instrument, e.g. a stock (AAPL) or an ETF (QQQ)")
                    String symbol,
            @ToolArg(description = "Currency of the prices (ISO 4217 code)", defaultValue = "EUR") Currency currency) {

        try {
            return mcpInstrumentService.getQuote(symbol, currency);
        } catch (MarketDataException e) {
            throw new ToolCallException(e.getMessage(), e);
        }
    }
}
