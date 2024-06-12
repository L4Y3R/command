package com.demo.command.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnknownErrorException.class)
    public ResponseEntity<Object> exception(UnknownErrorException exception) {
        return new ResponseEntity<>("Unknown error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DeviceNotAuthorizedException.class)
    public ResponseEntity<Object> exception(DeviceNotAuthorizedException exception) {
        return new ResponseEntity<>("A device associated with this user does not exist", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DeviceNotFoundException.class)
    public ResponseEntity<Object> exception(DeviceNotFoundException exception) {
        return new ResponseEntity<>("Device not found", HttpStatus.NOT_FOUND);
    }
}
