package com.test.truproxyapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class JsonResultsProcessingException extends RuntimeException {

    public JsonResultsProcessingException(String message, Throwable e) {
        super(message, e);
    }
}
