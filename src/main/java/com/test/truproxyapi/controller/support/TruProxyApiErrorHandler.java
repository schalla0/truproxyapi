package com.test.truproxyapi.controller.support;

import com.test.truproxyapi.exception.JsonResultsProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Primary
@ControllerAdvice
public class TruProxyApiErrorHandler {

    @ExceptionHandler(JsonResultsProcessingException.class)
    public ResponseEntity<String> handleJsonResultsProcessingException(JsonResultsProcessingException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Json results processing exception: " + ex.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Constraint violation exception: " + ex.getMessage());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<String> errors = new ArrayList<>();
        ex.getAllErrors().forEach(err -> errors.add(err.getDefaultMessage()));
        Map<String, List<String>> result = new HashMap<>();
        result.put("errors", errors);
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<?> handleRequestHeaderException(MissingRequestHeaderException ex, HttpServletRequest request) {
        Map<String, List<String>> result = new HashMap<>();
        result.put("errors", List.of(ex.getMessage()));
        return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
    }
}
