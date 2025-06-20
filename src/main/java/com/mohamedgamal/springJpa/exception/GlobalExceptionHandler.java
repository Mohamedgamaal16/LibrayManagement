package com.mohamedgamal.springJpa.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {




    @ExceptionHandler(LibraryManagmentAPIException.class)
    public ResponseEntity<?> handleApiException(LibraryManagmentAPIException ex){

        ErorrResponse errorResponse = new ErorrResponse(
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                ex.getMessage(),
                new Date()
        ) ;


        return new ResponseEntity<>(errorResponse, HttpStatus.SERVICE_UNAVAILABLE);

    }




}
