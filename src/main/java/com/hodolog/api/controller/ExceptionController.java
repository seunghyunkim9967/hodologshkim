package com.hodolog.api.controller;

import com.hodolog.api.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.Map;
import java.util.HashMap;

@Slf4j
@ControllerAdvice
public class ExceptionController {
    @ResponseStatus(HttpStatus.BAD_REQUEST)//BAD_REQUEST : 400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException e) {
        //
//        MethodArgumentNotValidException
//        log.info("exceptionHandler error : " + e);
            ErrorResponse response =  new ErrorResponse("400","잘못된 요청입니다.");
            for (FieldError filedError : e.getFieldErrors()) {
                response.addValidation(filedError.getField(), filedError.getDefaultMessage());
            }
//        FieldError filedError = e.getFieldError();
//        String field = filedError.getField();
//        String massage = filedError.getDefaultMessage();
//
//        Map<String, String> response = new HashMap<>();
//
//        response.put(field, massage);
//        return response;
        return response;
    }
}
