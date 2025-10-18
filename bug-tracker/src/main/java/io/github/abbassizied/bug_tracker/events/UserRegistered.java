package io.github.abbassizied.bug_tracker.events;

import java.util.List;

public record UserRegistered(Long userId, String username, List<String> role) {
}
