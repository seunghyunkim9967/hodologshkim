package com.hodolog.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
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
//@RequiredArgsConstructor
//@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class ErrorResponse {

    private final String code;
    private  String massage;

    private final Map<String, String> validation = new HashMap<>();

    @Builder
    public ErrorResponse(String code, String massage) {
        this.code = code;
        this.massage = massage;
    }



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
