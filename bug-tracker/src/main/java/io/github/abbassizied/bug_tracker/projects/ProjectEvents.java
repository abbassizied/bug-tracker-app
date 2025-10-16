package io.github.abbassizied.bug_tracker.projects;

public class ProjectEvents {
    public record ProjectCreated(Long projectId, String name, Long ownerId) {
    }

    public record ProjectMemberAdded(Long projectId, Long userId) {
    }

    public record ProjectArchived(Long projectId) {
    }

    public record ProjectDeletedEvent(Long projectId) {
    }

    public record UserDeletedEvent(Long userId) {
    }
}
