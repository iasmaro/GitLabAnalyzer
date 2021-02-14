// adapted from: https://github.com/RameshMF/springboot-crud-restful-webservices
// adapted from: https://github.com/brunocleite/spring-boot-exception-handling

package com.haumea.gitanalyzer.exception;

import org.gitlab4j.api.GitLabApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    // handling specific exception
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> resourceNotFoundHandling(ResourceNotFoundException exception, WebRequest request){

        return buildResponseEntity(buildStandardApiError(exception, request, HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(ResourceAlredyExistException.class)
    public ResponseEntity<Object> resourceAlredyExistExceptionHandling(ResourceAlredyExistException exception,
                                                                  WebRequest request){
//        ErrorDetails errorDetails =
//                new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false), HttpStatus.BAD_REQUEST);
//        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);

        return buildResponseEntity(buildStandardApiError(exception, request, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(GitLabApiException.class)
    public ResponseEntity<Object> gitLabApiExceptionHandling(GitLabApiException exception, WebRequest request){
        ErrorDetails errorDetails =
                new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> constraintViolationExceptionHandling(ConstraintViolationException exception, WebRequest request){
//        ErrorDetails errorDetails =
//                new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false), HttpStatus.BAD_REQUEST);
//        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST,
                "Validation error",
                request.getDescription(false),
                exception);
        apiError.addValidationErrors(exception.getConstraintViolations());

        return buildResponseEntity(apiError);
    }

    // handling global exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> globalExceptionHandling(Exception exception, WebRequest request){
        ErrorDetails errorDetails =
                new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError){
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    private ApiError buildStandardApiError(Throwable exception, WebRequest request, HttpStatus status){
        return new ApiError(
                status,
                exception.getMessage(),
                request.getDescription(false),
                exception);
    }
}
