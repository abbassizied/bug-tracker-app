package io.github.abbassizied.bug_tracker.user.dto;

import java.time.Instant;

public record UserResponse(
        Long id,
        String username,
        String email,
        String role,
        Instant createdAt,
        Instant updatedAt) {

}
