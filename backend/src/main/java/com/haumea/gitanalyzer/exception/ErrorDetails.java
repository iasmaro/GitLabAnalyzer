// adapted from: https://github.com/RameshMF/springboot-crud-restful-webservices

package com.haumea.gitanalyzer.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
public class ErrorDetails {
    private Date timestamp;
    private HttpStatus status;
    private String message;
    private String debugMessage;
    private String endpoint;

    private ErrorDetails() {
        timestamp = new Date();
    }

    public ErrorDetails(HttpStatus status, String message, String endpoint, Throwable exception) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = exception.getLocalizedMessage();
        this.endpoint = endpoint;
    }

}
