package com.hodolog.api.exception;

import lombok.Getter;

@Getter
public class InvalidRequest extends HodologException {
    private static final String MESSAGE = "잘못된 요청입니다.";

    private String fieldName;
    private String massage;

    public InvalidRequest() {
        super(MESSAGE);
    }

    public InvalidRequest(String fieldName, String massage) {
        super(MESSAGE);
//        this.fieldName = fieldName;
//        this.massage = massage;
        addValidation(fieldName, massage);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
