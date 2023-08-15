package com.softserve.todolistrestapi.exception;

import com.softserve.todolistrestapi.util.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NullEntityReferenceException.class)
    public ResponseEntity<ErrorResponse> nullEntityReferenceExceptionHandler(NullEntityReferenceException exception) {
        final ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> entityNotFoundExceptionHandler(EntityNotFoundException exception) {
        final ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityNotCreatedException.class)
    public ResponseEntity<ErrorResponse> entityNotCreatedExceptionHandler(EntityNotCreatedException exception) {
        final ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ErrorResponse> duplicateEmailExceptionHandler(DuplicateEmailException exception) {
        final ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception exception) {
        final ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
