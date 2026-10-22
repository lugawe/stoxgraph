package org.stoxgraph.core.ports;

import java.math.BigDecimal;

import org.stoxgraph.core.domain.money.Currency;

public interface ExchangeRateService {

    /** Returns how many units of {@code to} one unit of {@code from} is worth. */
    BigDecimal getExchangeRate(Currency from, Currency to);
}
