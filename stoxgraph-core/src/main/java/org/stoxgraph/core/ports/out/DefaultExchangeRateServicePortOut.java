package org.stoxgraph.core.ports.out;

import java.math.BigDecimal;
import java.util.Objects;

import org.stoxgraph.core.domain.money.Currency;
import org.stoxgraph.core.ports.out.provider.ExchangeRateProvider;
import org.stoxgraph.core.service.logging.Logger;

public final class DefaultExchangeRateServicePortOut implements ExchangeRateServicePortOut {

    private final Logger logger;
    private final ExchangeRateProvider exchangeRateProvider;

    public DefaultExchangeRateServicePortOut(Logger logger, ExchangeRateProvider exchangeRateProvider) {
        this.logger = Objects.requireNonNull(logger);
        this.exchangeRateProvider = Objects.requireNonNull(exchangeRateProvider);
    }

    @Override
    public BigDecimal getExchangeRate(Currency from, Currency to) {

        logger.debug("getExchangeRate {} to {}", from, to);

        return exchangeRateProvider.getExchangeRate(from, to);
    }
}
