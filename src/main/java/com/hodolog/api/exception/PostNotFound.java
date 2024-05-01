package com.hodolog.api.exception;

public class PostNotFound extends RuntimeException{

    public PostNotFound(String message) {
        super(message);
    }

    public PostNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
