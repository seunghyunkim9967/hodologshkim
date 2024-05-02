package com.hodolog.api.exception;

public class PostNotFound extends RuntimeException{

    private static final String MASSAGE = "존재하지 않는 글입니다.";

    public PostNotFound() {
        super(MASSAGE);
    }

//    public PostNotFound(String message, Throwable cause) {
//        super(MASSAGE, cause);
//    }
}
