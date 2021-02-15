package com.haumea.gitanalyzer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceAlredyExistException extends RuntimeException{
    public ResourceAlredyExistException(String message) {
        super(message);
    }
}
