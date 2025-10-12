package io.github.abbassizied.bug_tracker.common.api;

/**
 * Standard response wrapper for successful API responses.
 * Use records for immutability and simplicity.
 */
public record ApiResponse<T>(
        boolean success,
        String message,
        T data) {
    public static <T> ApiResponse<T> ok(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(true, "Operation successful", data);
    }

    public static <T> ApiResponse<T> fail(String message) {
        return new ApiResponse<>(false, message, null);
    }
}