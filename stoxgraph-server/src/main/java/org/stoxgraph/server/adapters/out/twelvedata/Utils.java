package org.stoxgraph.server.adapters.out.twelvedata;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.twelvedata.client.ApiException;
import com.twelvedata.client.errors.TwelvedataApiException;
import com.twelvedata.client.model.IntervalEnum;
import org.stoxgraph.core.domain.marketdata.Interval;
import org.stoxgraph.core.domain.money.Currency;
import org.stoxgraph.core.exception.MarketDataException;
import org.stoxgraph.core.exception.UnknownSymbolException;

public final class Utils {

    private Utils() {}

    public interface ApiCall<T> {
        T call() throws ApiException;
    }

    public static <T> T call(ApiCall<T> call) {
        try {
            return call.call();
        } catch (TwelvedataApiException.NotFoundException e) {
            throw new UnknownSymbolException(e.getMessage(), e);
        } catch (TwelvedataApiException | ApiException e) {
            throw new MarketDataException("Twelve Data API request failed", e);
        }
    }

    public static BigDecimal decimal(String value) {
        return value != null ? new BigDecimal(value) : null;
    }

    public static long number(String value) {
        return value != null ? Long.parseLong(value) : 0;
    }

    public static Currency currency(String code) {
        try {
            return Currency.valueOf(code);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new MarketDataException("Unsupported currency: " + code, e);
        }
    }

    public static long timestamp(String value) {
        LocalDateTime dateTime = value.length() == 10
                ? LocalDate.parse(value).atStartOfDay()
                : LocalDateTime.parse(value.replace(' ', 'T'));
        return dateTime.toEpochSecond(ZoneOffset.UTC);
    }

    public static IntervalEnum toIntervalEnum(Interval interval) {
        return switch (interval) {
            case MINUTES_1 -> IntervalEnum._1MIN;
            case MINUTES_5 -> IntervalEnum._5MIN;
            case HOURS_1 -> IntervalEnum._1H;
            case DAY_1 -> IntervalEnum._1DAY;
            case WEEK_1 -> IntervalEnum._1WEEK;
        };
    }
}
