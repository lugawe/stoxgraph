package org.stoxgraph.server.logging;

import org.slf4j.Logger;
import org.slf4j.MDC;

public record Slf4jLogger(Logger logger) implements org.stoxgraph.core.service.logging.Logger {

    @Override
    public String getName() {
        return logger.getName();
    }

    @Override
    public ContextCloseable putContext(String key, Object context) {
        MDC.MDCCloseable c = MDC.putCloseable(key, context.toString());
        return c::close;
    }

    @Override
    public void debug(String message) {
        logger.debug(message);
    }

    @Override
    public void debug(String message, Object... args) {
        logger.debug(message, args);
    }

    @Override
    public void debug(String message, Throwable t) {
        logger.debug(message, t);
    }

    @Override
    public void info(String message) {
        logger.info(message);
    }

    @Override
    public void info(String message, Object... args) {
        logger.info(message, args);
    }

    @Override
    public void info(String message, Throwable t) {
        logger.info(message, t);
    }

    @Override
    public void warn(String message) {
        logger.warn(message);
    }

    @Override
    public void warn(String message, Object... args) {
        logger.warn(message, args);
    }

    @Override
    public void warn(String message, Throwable t) {
        logger.warn(message, t);
    }

    @Override
    public void error(String message) {
        logger.error(message);
    }

    @Override
    public void error(String message, Object... args) {
        logger.error(message, args);
    }

    @Override
    public void error(String message, Throwable t) {
        logger.error(message, t);
    }
}
