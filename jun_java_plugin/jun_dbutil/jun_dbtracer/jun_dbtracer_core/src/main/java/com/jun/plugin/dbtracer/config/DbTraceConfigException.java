package com.jun.plugin.dbtracer.config;

public class DbTraceConfigException extends RuntimeException {

    private static final long serialVersionUID = -3414592229202459667L;

    public DbTraceConfigException(String cause) {
        super(cause);
    }

    public DbTraceConfigException(Throwable cause) {
        super(cause);
    }

}
