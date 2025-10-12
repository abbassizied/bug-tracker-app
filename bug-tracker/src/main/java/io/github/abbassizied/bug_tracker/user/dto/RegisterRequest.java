package io.github.abbassizied.bug_tracker.user.dto;

import jakarta.validation.constraints.*;

public record RegisterRequest(
                @NotBlank @Size(min = 3, max = 50) String username,
                @NotBlank @Email String email,
                @NotBlank @Size(min = 8, max = 128) String password) {
}