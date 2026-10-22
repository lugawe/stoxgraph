package org.stoxgraph.core.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable t) {
        super(message, t);
    }

    public NotFoundException(Throwable t) {
        super(t);
    }
}
