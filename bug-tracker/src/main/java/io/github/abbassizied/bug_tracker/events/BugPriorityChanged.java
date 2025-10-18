package io.github.abbassizied.bug_tracker.events;

public record BugPriorityChanged(Long bugId, String newPriority) {
}
