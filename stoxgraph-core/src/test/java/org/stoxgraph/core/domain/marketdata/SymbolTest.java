package org.stoxgraph.core.domain.marketdata;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.stoxgraph.core.exception.marketdata.InvalidRequestException;

public class SymbolTest {

    public SymbolTest() {}

    @Test
    void test1() {
        Assertions.assertEquals(new Symbol("AAPL"), new Symbol(" aapl "));
    }

    @Test
    void test2() {
        Assertions.assertThrows(InvalidRequestException.class, () -> new Symbol(null));
        Assertions.assertThrows(InvalidRequestException.class, () -> new Symbol(" "));
    }
}
