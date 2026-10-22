package org.stoxgraph.core.ports.in;

import java.math.BigDecimal;
import java.util.Objects;

import org.stoxgraph.core.domain.money.Currency;
import org.stoxgraph.core.ports.out.ExchangeRateServicePortOut;
import org.stoxgraph.core.service.logging.Logger;

public final class DefaultExchangeRateServicePortIn implements ExchangeRateServicePortIn {

    private final Logger logger;
    private final ExchangeRateServicePortOut exchangeRateServicePortOut;

    public DefaultExchangeRateServicePortIn(Logger logger, ExchangeRateServicePortOut exchangeRateServicePortOut) {
        this.logger = Objects.requireNonNull(logger);
        this.exchangeRateServicePortOut = Objects.requireNonNull(exchangeRateServicePortOut);
    }

    @Override
    public BigDecimal getExchangeRate(Currency from, Currency to) {

        logger.debug("getExchangeRate {} to {}", from, to);

        return exchangeRateServicePortOut.getExchangeRate(from, to);
    }
}
