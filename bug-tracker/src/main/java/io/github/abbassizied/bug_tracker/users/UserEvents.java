package io.github.abbassizied.bug_tracker.users;

import java.util.List;

public class UserEvents {
    public record UserRegistered(Long userId, String username, List<String> role) {}
    public record UserRoleChanged(Long userId, List<String> newRole) {}
    public record UserDeactivated(Long userId) {}    
}
