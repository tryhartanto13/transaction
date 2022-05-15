package com.th.transaction.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiError> genericException(GeneralException generalException) {
        return new ResponseEntity<>(ApiError.builder()
                .errCode("DB-01")
                .errDesc("Unknown Error")
                .refNo(generalException.getRefNo())
                .build(), HttpStatus.BAD_REQUEST);
    }
}
