package io.github.abbassizied.bug_tracker.user.dto;

import jakarta.validation.constraints.*;

public record LoginRequest(
        @NotBlank String username,
        @NotBlank String password) {

}
