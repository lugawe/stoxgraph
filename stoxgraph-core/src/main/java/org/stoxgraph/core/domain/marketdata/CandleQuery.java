package org.stoxgraph.core.domain.marketdata;

import java.time.LocalDate;

import org.stoxgraph.core.exception.InvalidRequestException;

/**
 * Which candles of an instrument to return.
 *
 * @param interval length of one candle
 * @param start first day to include, or {@code null} for no lower bound
 * @param end last day to include, or {@code null} for no upper bound
 * @param limit maximum number of candles
 */
public record CandleQuery(Interval interval, LocalDate start, LocalDate end, long limit) {

    public CandleQuery {
        if (interval == null) {
            throw new InvalidRequestException("Interval must not be null");
        }
        if (limit <= 0) {
            throw new InvalidRequestException("Limit must be positive, but was " + limit);
        }
        if (start != null && end != null && start.isAfter(end)) {
            throw new InvalidRequestException("Start " + start + " must not be after end " + end);
        }
    }
}
