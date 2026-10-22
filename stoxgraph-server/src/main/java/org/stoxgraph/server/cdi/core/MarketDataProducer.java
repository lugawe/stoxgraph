package org.stoxgraph.server.cdi.core;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

import org.stoxgraph.core.ports.in.DefaultMarketDataServicePortIn;
import org.stoxgraph.core.ports.in.MarketDataServicePortIn;
import org.stoxgraph.core.ports.out.ExchangeRateServicePortOut;
import org.stoxgraph.core.ports.out.MarketDataServicePortOut;

@ApplicationScoped
public class MarketDataProducer {

    @Inject
    private MarketDataServicePortOut marketDataServicePortOut;

    @Inject
    private ExchangeRateServicePortOut exchangeRateServicePortOut;

    public MarketDataProducer() {}

    @Produces
    public MarketDataServicePortIn produce() {
        return new DefaultMarketDataServicePortIn(marketDataServicePortOut, exchangeRateServicePortOut);
    }
}
