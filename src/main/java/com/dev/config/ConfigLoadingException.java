package com.dev.config;

public class ConfigLoadingException extends Exception {
    public ConfigLoadingException() {
        super();
    }

    public ConfigLoadingException(String message) {
        super(message);
    }

    public ConfigLoadingException(String message, Throwable cause) {
        super(message, cause);
    }
}
