package org.stoxgraph.core.ports.out;

import java.math.BigDecimal;

import org.stoxgraph.core.domain.money.Currency;

public interface ExchangeRateServicePortOut {

    /** Returns how many units of {@code to} one unit of {@code from} is worth. */
    BigDecimal getExchangeRate(Currency from, Currency to);
}
