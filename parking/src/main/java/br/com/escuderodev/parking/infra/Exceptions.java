package br.com.escuderodev.parking.infra;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class Exceptions {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity error404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity error400(MethodArgumentNotValidException e) {
        var error = e.getFieldErrors();
        return ResponseEntity.badRequest().body(error.stream().map(errorValidation::new).toList());
    }

    //    dto para tratar erros
    private record errorValidation(
            String field,
            String message){
        public errorValidation(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }

    }
}
