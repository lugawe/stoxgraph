package org.stoxgraph.core.exception;

public class ProviderException extends RuntimeException {

    public ProviderException() {
        super();
    }

    public ProviderException(String message) {
        super(message);
    }

    public ProviderException(String message, Throwable t) {
        super(message, t);
    }

    public ProviderException(Throwable t) {
        super(t);
    }
}
