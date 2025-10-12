package io.github.abbassizied.bug_tracker.common.api;

import java.time.Instant;

/**
 * Represents a standardized error response for API exceptions.
 */
public record ErrorResponse(
        Instant timestamp,
        int status,
        String error,
        String message,
        String path) {
    public static ErrorResponse of(int status, String error, String message, String path) {
        return new ErrorResponse(Instant.now(), status, error, message, path);
    }
}