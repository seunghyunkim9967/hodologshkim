package com.hodolog.api.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/*
* {
*   "code" : "400"
*   "massage" : "잘못된 요청"
*   "validation" : {
*       "title": "값 입력"
*   }
* */

@Getter
@RequiredArgsConstructor
public class ErrorResponse {

    private final String code;
    private final String massage;

    private final Map<String, String> validation = new HashMap<>();

    public void addValidation(String filedName, String errorMassage) {
//        ValidationTuple tuple = new ValidationTuple(filedName, errorMassage);
        this.validation.put(filedName, errorMassage);
    }

//    @RequiredArgsConstructor
//    private class ValidationTuple {
//        private final String fieldName;
//        private final String errorMassage;
//    }

}
