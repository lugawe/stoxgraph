package org.stoxgraph.core.service.logging;

import java.io.Closeable;

public interface Logger {

    interface ContextCloseable extends Closeable {

        @Override
        void close();
    }

    String getName();

    ContextCloseable putContext(String key, Object context);

    void debug(String message);

    void debug(String message, Object... args);

    void debug(String message, Throwable t);

    void info(String message);

    void info(String message, Object... args);

    void info(String message, Throwable t);

    void warn(String message);

    void warn(String message, Object... args);

    void warn(String message, Throwable t);

    void error(String message);

    void error(String message, Object... args);

    void error(String message, Throwable t);
}
