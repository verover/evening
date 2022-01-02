package com.enigmacamp.evening.controller;

import com.enigmacamp.evening.exception.InvalidInputException;
import com.enigmacamp.evening.exception.NotFoundException;
import com.enigmacamp.evening.payload.response.ErrorResponse;
import com.enigmacamp.evening.util.WebResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(NotFoundException notFoundException){
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorResponse response = new ErrorResponse(
                status.value(), status.getReasonPhrase(), notFoundException.getMessage()
        );
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(value = {InvalidInputException.class})
    public ResponseEntity<Object> handleInvalidInput(InvalidInputException invalidInputException){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorResponse response = new ErrorResponse(
                status.value(), status.getReasonPhrase(), invalidInputException.getMessage()
        );
        return new ResponseEntity<>(response, status);
    }
}
