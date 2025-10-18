package io.github.abbassizied.bug_tracker.events;

public record BugResolved(Long bugId, Long resolverId, Long projectId) {
}
