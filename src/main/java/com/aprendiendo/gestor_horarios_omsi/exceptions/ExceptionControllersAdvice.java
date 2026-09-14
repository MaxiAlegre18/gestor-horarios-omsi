package com.aprendiendo.gestor_horarios_omsi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class ExceptionControllersAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> exceptionIllegalArgumentHandler(IllegalArgumentException ex) {
        return ResponseEntity
                .badRequest()
                .body("Error de validación: " + ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno del servidor: " + ex.getMessage());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        return ResponseEntity.badRequest().body("Error: El archivo es demasiado grande. El límite es 1MB.");
    }
}
