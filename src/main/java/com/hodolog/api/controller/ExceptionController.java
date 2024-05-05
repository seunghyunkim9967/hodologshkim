package com.hodolog.api.controller;

import com.hodolog.api.exception.HodologException;
import com.hodolog.api.exception.InvalidRequest;
import com.hodolog.api.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

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
            ErrorResponse response =  ErrorResponse.builder()
                    .code("400")
                    .massage("잘못된 요청입니다.")
                    .build();


                    ;
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

//    @ResponseStatus(HttpStatus.NOT_FOUND)//NOT_FOUND : 404
    @ExceptionHandler(HodologException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> HodologException(HodologException e) {

        int statusCode = e.getStatusCode();

        ErrorResponse body =  ErrorResponse.builder()
                .code(String.valueOf(statusCode))
                .massage(e.getMessage())
                .validation(e.getValidation())
                .build();

//            body.addValidation("validationTest", "의도한 404 Valid Error");
//        if (e instanceof InvalidRequest) {
//            InvalidRequest invalidRequest = (InvalidRequest) e;
//            String fieldName = invalidRequest.getFieldName();
//            String massage = invalidRequest.getMassage();
//            body.addValidation(fieldName,massage);
//        }


        ResponseEntity<ErrorResponse> response = ResponseEntity.status(statusCode)
                .body(body);


        return response;
    }

}
