// adapted from: https://github.com/RameshMF/springboot-crud-restful-webservices
// adapted from: https://github.com/brunocleite/spring-boot-exception-handling

package com.haumea.gitanalyzer.exception;

import org.gitlab4j.api.GitLabApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // handling specific exception
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> resourceNotFoundHandling(ResourceNotFoundException exception, WebRequest request){

        return buildResponseEntity(buildStandardError(exception, request, HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(ResourceAlredyExistException.class)
    public ResponseEntity<Object> resourceAlredyExistExceptionHandling(ResourceAlredyExistException exception,
                                                                  WebRequest request){

        return buildResponseEntity(buildStandardError(exception, request, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(GitLabApiException.class)
    public ResponseEntity<Object> gitLabApiExceptionHandling(GitLabApiException exception, WebRequest request){

        return buildResponseEntity(buildStandardError(exception, request, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(GitLabRuntimeException.class)
    public ResponseEntity<Object> gitLabRuntimeExceptionHandling(GitLabRuntimeException exception, WebRequest request){

        return buildResponseEntity(buildStandardError(exception, request, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> constraintViolationExceptionHandling(ConstraintViolationException exception, WebRequest request){

        return buildResponseEntity(buildStandardError(exception, request, HttpStatus.BAD_REQUEST));

    }

    // handling global exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> globalExceptionHandling(Exception exception, WebRequest request){

        return buildResponseEntity(buildStandardError(exception, request, HttpStatus.INTERNAL_SERVER_ERROR));
    }

    // helper methods
    private ResponseEntity<Object> buildResponseEntity(ErrorDetails error){
        return new ResponseEntity<>(error, error.getStatus());
    }

    private ErrorDetails buildStandardError(Throwable exception, WebRequest request, HttpStatus status){
        return new ErrorDetails(
                status,
                exception.getMessage(),
                request.getDescription(false),
                exception);
    }
}
