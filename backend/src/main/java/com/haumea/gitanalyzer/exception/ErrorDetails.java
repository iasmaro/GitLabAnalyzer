// adapted from: https://github.com/RameshMF/springboot-crud-restful-webservices

package com.haumea.gitanalyzer.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
public class ErrorDetails {
    private Date timestamp;
    private String message;
    private String debugMessage;
    private String endpoint;
    private HttpStatus status;

    private ErrorDetails() {
        timestamp = new Date();
    }

    public ErrorDetails(HttpStatus status, String message, String endpoint) {
        this();
        this.status = status;
        this.message = message;
        this.endpoint = endpoint;
    }

}
