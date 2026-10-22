package org.stoxgraph.server.adapters.out.provider.twelvedata;

import java.math.BigDecimal;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.stoxgraph.core.domain.money.Currency;
import org.stoxgraph.core.ports.out.ExchangeRateServicePortOut;

@ApplicationScoped
public class TwelveDataExchangeRateService implements ExchangeRateServicePortOut {

    private final TwelveDataClient client;

    @Inject
    public TwelveDataExchangeRateService(TwelveDataClient client) {
        this.client = client;
    }

    @Override
    public BigDecimal getExchangeRate(Currency from, Currency to) {
        return client.getExchangeRate(from, to);
    }
}
