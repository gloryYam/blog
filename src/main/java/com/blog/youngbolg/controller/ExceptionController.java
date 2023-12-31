package com.blog.youngbolg.controller;

import com.blog.youngbolg.exception.YoungBlogException;
import com.blog.youngbolg.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException e) {
        ErrorResponse response = ErrorResponse.builder()
                .code("400")
                .message("잘못된 요청입니다.")
                .build();


        for(FieldError fieldErrors : e.getFieldErrors()) {
            response.addValidation(fieldErrors.getField(), fieldErrors.getDefaultMessage());
        }

        return response;
    }

    @ResponseBody
    @ExceptionHandler(YoungBlogException.class)
    public ResponseEntity<ErrorResponse> youngBlogException(YoungBlogException e) {
        int statusCode = e.getStatusCode();

        ErrorResponse body = ErrorResponse.builder()
                .code(String.valueOf(statusCode))
                .message(e.getMessage())
                .validation(e.getValidation()) // 추가 ( 아래 if 대체 )
                .build();

        // Exception 마다 각기 다른 구현 사항이 있기 때문에 좋지 못하다.
//        if(e instanceof InvalidRequest) {
//            InvalidRequest invalidRequest = (InvalidRequest) e;
//            String fieldName = invalidRequest.getFieldName();
//            String message = invalidRequest.getMessage();
//            body.addValidation(fieldName, message);
//        }

        return ResponseEntity.status(statusCode)
                .body(body);

    }
}














