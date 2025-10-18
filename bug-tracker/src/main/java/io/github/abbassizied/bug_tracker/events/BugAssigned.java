package io.github.abbassizied.bug_tracker.events;

public record BugAssigned(Long bugId, Long assigneeId, Long projectId) {
}