package org.stoxgraph.core.exception;

import org.stoxgraph.core.domain.marketdata.Symbol;
import org.stoxgraph.core.domain.money.Currency;

public final class DomainExceptions {

    private DomainExceptions() {}

    public static NotFoundException createSymbolNotFound(Symbol symbol) {
        return new NotFoundException("Symbol not found: " + symbol);
    }

    public static NotFoundException createExchangeRateNotFound(Currency from, Currency to) {
        return new NotFoundException("Exchange rate not found: " + from + "/" + to);
    }
}
