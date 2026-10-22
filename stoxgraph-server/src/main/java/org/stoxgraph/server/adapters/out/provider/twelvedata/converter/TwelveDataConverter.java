package org.stoxgraph.server.adapters.out.provider.twelvedata.converter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import com.twelvedata.client.model.GetQuote200Response;
import com.twelvedata.client.model.GetTimeSeries200Response;
import com.twelvedata.client.model.IntervalEnum;
import com.twelvedata.client.model.TimeSeriesItem;
import org.stoxgraph.core.domain.marketdata.Candle;
import org.stoxgraph.core.domain.marketdata.CandleSeries;
import org.stoxgraph.core.domain.marketdata.Interval;
import org.stoxgraph.core.domain.marketdata.Quote;
import org.stoxgraph.core.domain.marketdata.Symbol;
import org.stoxgraph.core.domain.money.Currency;
import org.stoxgraph.core.exception.MarketDataException;

@ApplicationScoped
public class TwelveDataConverter {

    @Inject
    public TwelveDataConverter() {}

    public CandleSeries toCandleSeries(Symbol symbol, Interval interval, GetTimeSeries200Response timeSeries) {

        List<Candle> candles =
                timeSeries.getValues().stream().map(this::toCandle).toList();
        Currency currency = toCurrency(timeSeries.getMeta().getCurrency());

        return new CandleSeries(symbol, interval, currency, candles);
    }

    public Candle toCandle(TimeSeriesItem item) {

        return new Candle(
                toTimestamp(item.getDatetime()),
                toDecimal(item.getOpen()),
                toDecimal(item.getHigh()),
                toDecimal(item.getLow()),
                toDecimal(item.getClose()),
                toNumber(item.getVolume()));
    }

    public Quote toQuote(GetQuote200Response quote) {

        return new Quote(
                Objects.requireNonNullElse(quote.getLastQuoteAt(), quote.getTimestamp()),
                new Symbol(quote.getSymbol()),
                quote.getName(),
                quote.getExchange(),
                toCurrency(quote.getCurrency()),
                toDecimal(quote.getClose()),
                toDecimal(quote.getOpen()),
                toDecimal(quote.getHigh()),
                toDecimal(quote.getLow()),
                toDecimal(quote.getPreviousClose()),
                toDecimal(quote.getChange()),
                toDecimal(quote.getPercentChange()),
                toNumber(quote.getVolume()),
                quote.getIsMarketOpen());
    }

    public IntervalEnum toIntervalEnum(Interval interval) {

        return switch (interval) {
            case MINUTES_1 -> IntervalEnum._1MIN;
            case MINUTES_5 -> IntervalEnum._5MIN;
            case HOURS_1 -> IntervalEnum._1H;
            case DAY_1 -> IntervalEnum._1DAY;
            case WEEK_1 -> IntervalEnum._1WEEK;
        };
    }

    public Currency toCurrency(String code) {

        try {
            return Currency.valueOf(code);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new MarketDataException("Unsupported currency: " + code, e);
        }
    }

    private static BigDecimal toDecimal(String value) {
        return value != null ? new BigDecimal(value) : null;
    }

    private static long toNumber(String value) {
        return value != null ? Long.parseLong(value) : 0;
    }

    private static long toTimestamp(String value) {
        LocalDateTime dateTime = value.length() == 10
                ? LocalDate.parse(value).atStartOfDay()
                : LocalDateTime.parse(value.replace(' ', 'T'));
        return dateTime.toEpochSecond(ZoneOffset.UTC);
    }
}
