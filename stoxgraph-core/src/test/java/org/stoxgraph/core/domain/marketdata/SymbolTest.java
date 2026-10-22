package org.stoxgraph.core.domain.marketdata;

import org.stoxgraph.core.exception.ValidationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SymbolTest {

    public SymbolTest() {}

    @Test
    void test1() {
        Assertions.assertEquals(new Symbol("AAPL"), new Symbol(" aapl "));
    }

    @Test
    void test2() {
        Assertions.assertThrows(ValidationException.class, () -> new Symbol(null));
        Assertions.assertThrows(ValidationException.class, () -> new Symbol(" "));
    }
}
