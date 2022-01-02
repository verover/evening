package com.enigmacamp.evening.controller;

import com.enigmacamp.evening.exception.InvalidInputException;
import com.enigmacamp.evening.exception.NotFoundException;
import com.enigmacamp.evening.payload.response.ErrorResponse;
import com.enigmacamp.evening.util.WebResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class ErrorController {
    public ErrorController() {
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(NotFoundException exception) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorResponse response = new ErrorResponse(status.value(),status.getReasonPhrase(), exception.getMessage());
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler({InvalidInputException.class})
    public ResponseEntity<Object> handleInvalidException(InvalidInputException exception) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorResponse response = new ErrorResponse(status.value(),status.getReasonPhrase(), exception.getMessage());
        return new ResponseEntity<>(response, status);
    }
}
