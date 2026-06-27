package com.library.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Error response details
 */
@Data
@AllArgsConstructor
public class ErrorDetails {
    private String errorCode;
    private String errorMessage;
    private LocalDateTime timestamp;

    public ErrorDetails(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.timestamp = LocalDateTime.now();
    }
}