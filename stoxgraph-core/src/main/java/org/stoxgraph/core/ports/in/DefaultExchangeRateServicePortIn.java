package org.stoxgraph.core.ports.in;

import java.math.BigDecimal;

import org.stoxgraph.core.domain.money.Currency;
import org.stoxgraph.core.ports.out.ExchangeRateServicePortOut;

public final class DefaultExchangeRateServicePortIn implements ExchangeRateServicePortIn {

    private final ExchangeRateServicePortOut exchangeRateServicePortOut;

    public DefaultExchangeRateServicePortIn(ExchangeRateServicePortOut exchangeRateServicePortOut) {
        this.exchangeRateServicePortOut = exchangeRateServicePortOut;
    }

    @Override
    public BigDecimal getExchangeRate(Currency from, Currency to) {
        return exchangeRateServicePortOut.getExchangeRate(from, to);
    }
}
