package io.github.abbassizied.bug_tracker.users.dto;

import lombok.*;
import java.util.List;

@Getter
@AllArgsConstructor
public class JwtResponse {
    private final String token;
    private final String type = "Bearer";
    private final Long id;
    private final String username;
    private final String email;
    private final List<String> roles;
}