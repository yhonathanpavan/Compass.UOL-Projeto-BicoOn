package com.compass.bicoon.exceptions.forbiddenAccess;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ForbiddenAccessResourceExceptionHandler {

    @ExceptionHandler(ForbiddenAccessException.class)
    public ResponseEntity<ForbiddenAccessStandardError> forbiddenAccess(ForbiddenAccessException e, HttpServletRequest request){

        HttpStatus status = HttpStatus.FORBIDDEN;
        ForbiddenAccessStandardError err = new ForbiddenAccessStandardError(
                System.currentTimeMillis(),
                status.value(),
                "Acesso proibido",
                e.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

}
