package org.stoxgraph.server.cdi.core;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

import org.stoxgraph.core.ports.in.DefaultExchangeRateServicePortIn;
import org.stoxgraph.core.ports.in.DefaultMarketDataServicePortIn;
import org.stoxgraph.core.ports.in.ExchangeRateServicePortIn;
import org.stoxgraph.core.ports.in.MarketDataServicePortIn;
import org.stoxgraph.core.ports.out.DefaultExchangeRateServicePortOut;
import org.stoxgraph.core.ports.out.DefaultMarketDataServicePortOut;
import org.stoxgraph.core.ports.out.ExchangeRateServicePortOut;
import org.stoxgraph.core.ports.out.MarketDataServicePortOut;
import org.stoxgraph.core.ports.out.provider.ExchangeRateProvider;
import org.stoxgraph.core.ports.out.provider.MarketDataProvider;
import org.stoxgraph.core.service.logging.Logger;
import org.stoxgraph.server.logging.Slf4jLoggerFactory;

@ApplicationScoped
public class MarketDataProducer {

    @Inject
    private MarketDataProvider marketDataProvider;

    @Inject
    private ExchangeRateProvider exchangeRateProvider;

    public MarketDataProducer() {}

    @Produces
    public MarketDataServicePortIn produceMarketDataServicePortIn(
            MarketDataServicePortOut marketDataServicePortOut, ExchangeRateServicePortOut exchangeRateServicePortOut) {

        Logger logger = Slf4jLoggerFactory.getLogger(DefaultMarketDataServicePortIn.class);

        return new DefaultMarketDataServicePortIn(logger, marketDataServicePortOut, exchangeRateServicePortOut);
    }

    @Produces
    public ExchangeRateServicePortIn produceExchangeRateServicePortIn(
            ExchangeRateServicePortOut exchangeRateServicePortOut) {

        Logger logger = Slf4jLoggerFactory.getLogger(DefaultExchangeRateServicePortIn.class);

        return new DefaultExchangeRateServicePortIn(logger, exchangeRateServicePortOut);
    }

    @Produces
    public MarketDataServicePortOut produceMarketDataServicePortOut() {

        Logger logger = Slf4jLoggerFactory.getLogger(DefaultMarketDataServicePortOut.class);

        return new DefaultMarketDataServicePortOut(logger, marketDataProvider);
    }

    @Produces
    public ExchangeRateServicePortOut produceExchangeRateServicePortOut() {

        Logger logger = Slf4jLoggerFactory.getLogger(DefaultExchangeRateServicePortOut.class);

        return new DefaultExchangeRateServicePortOut(logger, exchangeRateProvider);
    }
}
