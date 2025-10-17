package io.github.abbassizied.bug_tracker.users.dto;

import java.util.Set;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @NotBlank
    private String username;

    @Email
    private String email;

    private String password;

    private Set<String> roles;
}
