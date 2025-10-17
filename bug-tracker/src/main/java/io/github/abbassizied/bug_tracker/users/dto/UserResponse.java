package io.github.abbassizied.bug_tracker.users.dto;

import java.util.List;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private List<String> roles;
    private boolean active;
}
