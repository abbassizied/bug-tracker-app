package io.github.abbassizied.bug_tracker.events;

public record BugCreated(Long bugId, Long projectId, Long reporterId, String title) {
}