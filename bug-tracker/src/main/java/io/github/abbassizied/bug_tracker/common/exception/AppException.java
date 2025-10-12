package io.github.abbassizied.bug_tracker.common.exception;

/**
 * Base runtime exception for the Bug Tracker application.
 * Can be extended for domain-specific exceptions.
 */
public class AppException extends RuntimeException {

    private final String errorCode;

    public AppException(String message) {
        super(message);
        this.errorCode = "GENERIC_ERROR";
    }

    public AppException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}