package org.stoxgraph.core.ports.out.provider;

import java.math.BigDecimal;

import org.stoxgraph.core.domain.money.Currency;

public interface ExchangeRateProvider {

    /** Returns how many units of {@code to} one unit of {@code from} is worth. */
    BigDecimal getExchangeRate(Currency from, Currency to);
}
