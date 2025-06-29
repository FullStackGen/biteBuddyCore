package com.bite.buddy.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
public class ApiError {

    private LocalDateTime timestamp;
    private String message;
    private String details;
    private Map<String, String> fieldErrors; // optional, for validation

    public ApiError() {
    }

    public ApiError(LocalDateTime timestamp, String message, String details, Map<String, String> fieldErrors) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
        this.fieldErrors = fieldErrors;
    }
}