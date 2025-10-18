package io.github.abbassizied.bug_tracker.events;

public record BugStatusChanged(Long bugId, String oldStatus, String newStatus) {
}
