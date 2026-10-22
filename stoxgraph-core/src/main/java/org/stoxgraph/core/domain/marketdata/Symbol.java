package org.stoxgraph.core.domain.marketdata;

import java.util.Locale;

import org.stoxgraph.core.exception.ValidationException;

/**
 * Ticker symbol of an instrument (e.g. a stock or an ETF), normalized to upper case without surrounding whitespace.
 *
 * @param value the normalized symbol, e.g. {@code AAPL}
 */
public record Symbol(String value) {

    public Symbol {
        if (value == null || value.isBlank()) {
            throw new ValidationException("Symbol must not be blank");
        }
        value = value.trim().toUpperCase(Locale.ROOT);
    }

    @Override
    public String toString() {
        return value;
    }
}
