package io.github.abbassizied.bug_tracker.events;

import java.util.List;

public record UserRoleChanged(Long userId, List<String> newRole) {
}