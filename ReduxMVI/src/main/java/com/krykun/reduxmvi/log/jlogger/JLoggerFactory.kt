package com.krykun.reduxmvi.log.jlogger;

public class JLoggerFactory {

    private JLoggerFactory() {
        throw new IllegalStateException("JLoggerFactory class");
    }

    public static JLogger getLogger(Class<?> clazz) {
        return new JLogger(clazz.getSimpleName());
    }
}

