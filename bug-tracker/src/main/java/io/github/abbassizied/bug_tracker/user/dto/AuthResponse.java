package io.github.abbassizied.bug_tracker.user.dto;

public record AuthResponse(String token, String tokenType, long expiresInSeconds) {
    public static AuthResponse of(String token, long expiresInSeconds) {
        return new AuthResponse(token, "Bearer", expiresInSeconds);
    }
}