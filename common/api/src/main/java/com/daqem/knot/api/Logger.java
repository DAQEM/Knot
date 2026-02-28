package com.daqem.knot.api;

import org.jetbrains.annotations.ApiStatus;
import org.slf4j.LoggerFactory;

@ApiStatus.Internal
public interface Logger {
    org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Constants.MOD_ID);

    static void info(String message) {
        LOGGER.info(message);
    }

    static void info(String message, Object... params) {
        LOGGER.info(message, params);
    }

    static void warn(String message) {
        LOGGER.warn(message);
    }

    static void warn(String message, Object... params) {
        LOGGER.warn(message, params);
    }

    static void error(String message) {
        LOGGER.error(message);
    }

    static void error(String message, Object... params) {
        LOGGER.error(message, params);
    }

    static void error(String message, Throwable t) {
        LOGGER.error(message, t);
    }
}
