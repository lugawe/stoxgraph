package org.stoxgraph.core.exception;

public class ValidationException extends RuntimeException {

    public ValidationException() {
        super();
    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable t) {
        super(message, t);
    }

    public ValidationException(Throwable t) {
        super(t);
    }
}
