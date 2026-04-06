package com.example.praksaprojekat.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex) {

        String message = ex.getConstraintViolations()
                .stream()
                .map(v -> v.getMessage())
                .reduce((m1, m2) -> m1 + ", " + m2)
                .orElse("Validation error");
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }



    @org.springframework.web.bind.annotation.ExceptionHandler(CandidateDoesntExist.class)
    public ResponseEntity<ErrorEntity> candidateNotFound(CandidateDoesntExist ex){
        ErrorEntity errorEntity = new ErrorEntity(ex.getMessage(), LocalDate.now());
        return new ResponseEntity<>(errorEntity,HttpStatus.NOT_FOUND);
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(SkillDoesntExist.class)
    public ResponseEntity<ErrorEntity> skillNotFound(SkillDoesntExist ex){
        ErrorEntity errorEntity = new ErrorEntity(ex.getMessage(), LocalDate.now());
        return new ResponseEntity<>(errorEntity,HttpStatus.NOT_FOUND);
    }

      @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        Map<String, String> errors = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

}