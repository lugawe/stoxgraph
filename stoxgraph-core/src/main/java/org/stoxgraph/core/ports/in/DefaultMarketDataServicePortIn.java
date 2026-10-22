package org.stoxgraph.core.ports.in;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.concurrent.Flow;
import java.util.function.Function;

import org.stoxgraph.core.domain.marketdata.CandleQuery;
import org.stoxgraph.core.domain.marketdata.CandleSeries;
import org.stoxgraph.core.domain.marketdata.Quote;
import org.stoxgraph.core.domain.marketdata.Symbol;
import org.stoxgraph.core.domain.money.Currency;
import org.stoxgraph.core.ports.out.ExchangeRateServicePortOut;
import org.stoxgraph.core.ports.out.MarketDataServicePortOut;
import org.stoxgraph.core.service.logging.Logger;
import org.stoxgraph.core.service.money.CurrencyConverter;
import org.stoxgraph.core.util.Publishers;

public final class DefaultMarketDataServicePortIn implements MarketDataServicePortIn {

    private final Logger logger;
    private final MarketDataServicePortOut marketDataServicePortOut;
    private final ExchangeRateServicePortOut exchangeRateServicePortOut;

    public DefaultMarketDataServicePortIn(
            Logger logger,
            MarketDataServicePortOut marketDataServicePortOut,
            ExchangeRateServicePortOut exchangeRateServicePortOut) {

        this.logger = Objects.requireNonNull(logger);
        this.marketDataServicePortOut = Objects.requireNonNull(marketDataServicePortOut);
        this.exchangeRateServicePortOut = Objects.requireNonNull(exchangeRateServicePortOut);
    }

    @Override
    public Quote getQuote(Symbol symbol, Currency currency) {

        logger.debug("getQuote {} in {}", symbol, currency);

        Quote quote = marketDataServicePortOut.getQuote(symbol);

        Function<BigDecimal, BigDecimal> converter = converter(quote.currency(), currency);

        return quote.updateCurrency(currency, converter);
    }

    @Override
    public CandleSeries getCandles(Symbol symbol, CandleQuery query, Currency currency) {

        logger.debug("getCandles {} {} in {}", symbol, query, currency);

        CandleSeries candles = marketDataServicePortOut.getCandles(symbol, query);

        Function<BigDecimal, BigDecimal> converter = converter(candles.currency(), currency);

        return candles.updateCurrency(currency, converter);
    }

    @Override
    public Flow.Publisher<Quote> streamQuotes(Symbol symbol, Currency currency) {

        logger.debug("streamQuotes {} in {}", symbol, currency);

        Flow.Publisher<Quote> stream = marketDataServicePortOut.streamQuotes(symbol);

        return Publishers.map(stream, q -> q.updateCurrency(currency, converter(q.currency(), currency)));
    }

    private Function<BigDecimal, BigDecimal> converter(Currency from, Currency to) {

        if (from.equals(to)) {
            return Function.identity();
        }

        BigDecimal exchangeRate = exchangeRateServicePortOut.getExchangeRate(from, to);
        logger.debug("converter {} to {} at rate {}", from, to, exchangeRate);
        return new CurrencyConverter(exchangeRate);
    }
}
