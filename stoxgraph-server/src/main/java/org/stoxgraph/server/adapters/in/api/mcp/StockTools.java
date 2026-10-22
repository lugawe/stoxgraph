package org.stoxgraph.server.adapters.in.api.mcp;

import java.time.LocalDate;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import io.quarkiverse.mcp.server.Tool;
import io.quarkiverse.mcp.server.ToolArg;
import io.quarkiverse.mcp.server.ToolCallException;
import org.stoxgraph.core.domain.marketdata.CandleQuery;
import org.stoxgraph.core.domain.marketdata.CandleSeries;
import org.stoxgraph.core.domain.marketdata.Interval;
import org.stoxgraph.core.domain.marketdata.Quote;
import org.stoxgraph.core.domain.marketdata.Symbol;
import org.stoxgraph.core.domain.money.Currency;
import org.stoxgraph.core.exception.marketdata.MarketDataException;
import org.stoxgraph.core.ports.in.MarketDataServicePortIn;

@Singleton
public class StockTools {

    private final MarketDataServicePortIn marketDataServicePortIn;

    @Inject
    public StockTools(MarketDataServicePortIn marketDataServicePortIn) {
        this.marketDataServicePortIn = marketDataServicePortIn;
    }

    @Tool(
            name = "get_stock_candles",
            description = "Returns the historical OHLC candles of a stock.",
            annotations =
                    @Tool.Annotations(
                            readOnlyHint = true,
                            destructiveHint = false,
                            idempotentHint = true,
                            openWorldHint = true))
    public CandleSeries getStockCandles(
            @ToolArg(description = "Ticker symbol of the stock (e.g. AAPL)") String symbol,
            @ToolArg(description = "Length of one candle", defaultValue = "DAY_1") Interval interval,
            @ToolArg(description = "First day to include (ISO date, e.g. 2026-01-31)", required = false)
                    LocalDate start,
            @ToolArg(description = "Last day to include (ISO date, e.g. 2026-01-31)", required = false) LocalDate end,
            @ToolArg(description = "Maximum number of candles", defaultValue = "30") long limit,
            @ToolArg(description = "Currency of the prices (ISO 4217 code)", defaultValue = "EUR") Currency currency) {

        Symbol s = new Symbol(symbol);
        CandleQuery candleQuery = new CandleQuery(interval, start, end, limit);
        try {
            return marketDataServicePortIn.getCandles(s, candleQuery, currency);
        } catch (MarketDataException e) {
            throw new ToolCallException(e.getMessage(), e);
        }
    }

    @Tool(
            name = "get_stock_quote",
            description = "Returns the current quote of a stock.",
            annotations =
                    @Tool.Annotations(
                            readOnlyHint = true,
                            destructiveHint = false,
                            idempotentHint = true,
                            openWorldHint = true))
    public Quote getStockQuote(
            @ToolArg(description = "Ticker symbol of the stock (e.g. AAPL)") String symbol,
            @ToolArg(description = "Currency of the prices (ISO 4217 code)", defaultValue = "EUR") Currency currency) {

        Symbol s = new Symbol(symbol);
        try {
            return marketDataServicePortIn.getQuote(s, currency);
        } catch (MarketDataException e) {
            throw new ToolCallException(e.getMessage(), e);
        }
    }
}
