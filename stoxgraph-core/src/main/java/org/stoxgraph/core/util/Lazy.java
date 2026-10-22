package org.stoxgraph.core.util;

import java.util.Objects;
import java.util.function.Supplier;

public final class Lazy<T> implements Supplier<T> {

    private final Supplier<T> delegate;

    private volatile boolean initialized;
    private T value;

    public Lazy(Supplier<T> delegate) {
        this.delegate = Objects.requireNonNull(delegate);
    }

    @Override
    public T get() {
        if (!initialized) {
            synchronized (this) {
                if (!initialized) {
                    value = delegate.get();
                    initialized = true;
                }
            }
        }
        return value;
    }

    public static <T> Lazy<T> of(Supplier<T> supplier) {
        return new Lazy<>(supplier);
    }
}
