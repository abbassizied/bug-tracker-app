package io.github.abbassizied.bug_tracker.bugs;

public class BugEvents {
    public record BugCreated(Long bugId, Long projectId, Long reporterId, String title) {}
    public record BugAssigned(Long bugId, Long assigneeId, Long projectId) {}
    public record BugStatusChanged(Long bugId, String oldStatus, String newStatus) {}
    public record BugPriorityChanged(Long bugId, String newPriority) {}
    public record BugResolved(Long bugId, Long resolverId) {}    
}
