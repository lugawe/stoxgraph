package org.stoxgraph.server.adapters.in.api.json.converter;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.stoxgraph.core.domain.marketdata.Candle;
import org.stoxgraph.core.domain.marketdata.CandleSeries;
import org.stoxgraph.core.domain.marketdata.Quote;
import org.stoxgraph.server.adapters.in.api.json.JsonCandle;
import org.stoxgraph.server.adapters.in.api.json.JsonCandleSeries;
import org.stoxgraph.server.adapters.in.api.json.JsonQuote;

@ApplicationScoped
public class JsonConverter {

    @Inject
    public JsonConverter() {}

    public JsonQuote toJsonQuote(Quote quote) {

        JsonQuote result = new JsonQuote();

        result.setTimestamp(quote.timestamp());
        result.setSymbol(quote.symbol().value());
        result.setName(quote.name());
        result.setExchange(quote.exchange());
        result.setCurrency(quote.currency().isoCode());
        result.setPrice(quote.price());
        result.setOpen(quote.open());
        result.setHigh(quote.high());
        result.setLow(quote.low());
        result.setPreviousClose(quote.previousClose());
        result.setChange(quote.change());
        result.setPercentChange(quote.percentChange());
        result.setVolume(quote.volume());
        result.setMarketOpen(quote.marketOpen());

        return result;
    }

    public JsonCandleSeries toJsonCandleSeries(CandleSeries candleSeries) {

        JsonCandleSeries result = new JsonCandleSeries();

        result.setSymbol(candleSeries.symbol().value());
        result.setInterval(candleSeries.interval().name());
        result.setCurrency(candleSeries.currency().isoCode());
        result.setAggregate(toJsonCandle(candleSeries.getAggregate()));
        result.setCandles(
                candleSeries.candles().stream().map(this::toJsonCandle).toList());

        return result;
    }

    public JsonCandle toJsonCandle(Candle candle) {

        if (candle == null) {
            return null;
        }

        JsonCandle result = new JsonCandle();

        result.setTimestamp(candle.timestamp());
        result.setOpen(candle.open());
        result.setHigh(candle.high());
        result.setLow(candle.low());
        result.setClose(candle.close());
        result.setVolume(candle.volume());
        result.setDirection(candle.getDirection().name());

        return result;
    }
}
