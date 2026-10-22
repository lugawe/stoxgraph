package org.stoxgraph.server.adapters.in.api.rest;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.ext.ParamConverter;
import jakarta.ws.rs.ext.ParamConverterProvider;
import jakarta.ws.rs.ext.Provider;

import org.stoxgraph.core.domain.money.Currency;

/** Lets JAX-RS read {@link Currency} params like {@code ?currency=EUR}. */
@Provider
public class CurrencyParamConverter implements ParamConverterProvider {

    private static final ParamConverter<Currency> CONVERTER = new ParamConverter<>() {

        @Override
        public Currency fromString(String value) {
            try {
                return Currency.valueOf(value);
            } catch (IllegalArgumentException e) {
                throw new BadRequestException("Unknown currency: " + value, e);
            }
        }

        @Override
        public String toString(Currency value) {
            return value.isoCode();
        }
    };

    public CurrencyParamConverter() {}

    @Override
    @SuppressWarnings("unchecked")
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
        return rawType == Currency.class ? (ParamConverter<T>) CONVERTER : null;
    }
}
