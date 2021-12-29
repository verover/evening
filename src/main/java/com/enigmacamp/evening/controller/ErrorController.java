package com.enigmacamp.evening.controller;

import com.enigmacamp.evening.exception.NotFoundException;
import com.enigmacamp.evening.util.WebResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ErrorController {


    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(NotFoundException exception){
        HttpStatus status = HttpStatus.NOT_FOUND;
        WebResponse<String> response = new WebResponse<>(
                exception.getMessage(),
                null
        );
        return new ResponseEntity<>(response,status);
    }

}
