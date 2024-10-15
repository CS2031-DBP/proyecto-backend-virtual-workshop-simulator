package com.example.proyecto;

import com.example.proyecto.exception.ErrorMessage;
import com.example.proyecto.exception.ResourceConflictException;
import com.example.proyecto.exception.ResourceForbiddenException;
import com.example.proyecto.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> resourceNotFound(ResourceNotFoundException e){
        ErrorMessage error = new ErrorMessage(e.getMessage(),HttpStatus.NOT_FOUND.toString());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceConflictException.class)
    public ResponseEntity<ErrorMessage> resourceConflict(ResourceConflictException e){
        ErrorMessage error = new ErrorMessage(e.getMessage(), HttpStatus.CONFLICT.toString());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(ResourceForbiddenException.class)
    public ResponseEntity<ErrorMessage> resourceForbidden(ResourceConflictException e){
        ErrorMessage error = new ErrorMessage(e.getMessage(), HttpStatus.FORBIDDEN.toString());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }
}
