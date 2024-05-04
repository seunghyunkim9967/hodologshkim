package com.hodolog.api.exception;

public abstract class HodologException extends RuntimeException {

    public HodologException(String message) {
        super(message);
    }

    public HodologException(String message, Throwable cause) {
        super(message, cause);
    }
}
