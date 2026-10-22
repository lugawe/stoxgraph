package org.stoxgraph.server.cdi.core;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

import org.stoxgraph.core.ports.in.DefaultExchangeRateServicePortIn;
import org.stoxgraph.core.ports.in.DefaultMarketDataServicePortIn;
import org.stoxgraph.core.ports.in.ExchangeRateServicePortIn;
import org.stoxgraph.core.ports.in.MarketDataServicePortIn;
import org.stoxgraph.core.ports.out.ExchangeRateServicePortOut;
import org.stoxgraph.core.ports.out.MarketDataServicePortOut;
import org.stoxgraph.core.service.logging.Logger;
import org.stoxgraph.server.logging.Slf4jLoggerFactory;

@ApplicationScoped
public class MarketDataProducer {

    @Inject
    private MarketDataServicePortOut marketDataServicePortOut;

    @Inject
    private ExchangeRateServicePortOut exchangeRateServicePortOut;

    public MarketDataProducer() {}

    @Produces
    public MarketDataServicePortIn produceMarketDataServicePortIn() {

        Logger logger = Slf4jLoggerFactory.getLogger(DefaultMarketDataServicePortIn.class);

        return new DefaultMarketDataServicePortIn(logger, marketDataServicePortOut, exchangeRateServicePortOut);
    }

    @Produces
    public ExchangeRateServicePortIn produceExchangeRateServicePortIn() {

        Logger logger = Slf4jLoggerFactory.getLogger(DefaultExchangeRateServicePortIn.class);

        return new DefaultExchangeRateServicePortIn(logger, exchangeRateServicePortOut);
    }
}
