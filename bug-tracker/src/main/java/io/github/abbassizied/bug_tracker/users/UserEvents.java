package io.github.abbassizied.bug_tracker.users;

public class UserEvents {
    public record UserRegistered(Long userId, String username, String role) {}
    public record UserRoleChanged(Long userId, String newRole) {}
    public record UserDeactivated(Long userId) {}    
}
