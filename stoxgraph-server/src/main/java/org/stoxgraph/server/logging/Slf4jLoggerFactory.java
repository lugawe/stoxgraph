package org.stoxgraph.server.logging;

import org.slf4j.LoggerFactory;
import org.stoxgraph.core.service.logging.Logger;

public final class Slf4jLoggerFactory {

    private Slf4jLoggerFactory() {}

    public static Logger getLogger(Class<?> declaringClass) {
        org.slf4j.Logger logger = LoggerFactory.getLogger(declaringClass);
        return new Slf4jLogger(logger);
    }
}
