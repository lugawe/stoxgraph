package org.stoxgraph.core.domain.marketdata;

import java.util.Locale;

import org.stoxgraph.core.exception.marketdata.InvalidRequestException;

/**
 * Ticker symbol of a stock, normalized to upper case without surrounding whitespace.
 *
 * @param value the normalized symbol, e.g. {@code AAPL}
 */
public record Symbol(String value) {

    public Symbol {
        if (value == null || value.isBlank()) {
            throw new InvalidRequestException("Symbol must not be blank");
        }
        value = value.trim().toUpperCase(Locale.ROOT);
    }

    @Override
    public String toString() {
        return value;
    }
}
