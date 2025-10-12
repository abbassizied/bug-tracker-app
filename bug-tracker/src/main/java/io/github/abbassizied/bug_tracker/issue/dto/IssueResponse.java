package io.github.abbassizied.bug_tracker.issue.dto;

import java.time.Instant;

public record IssueResponse(
        Long id,
        String title,
        String description,
        String status,
        Long reporterId,
        Long assigneeId,
        Instant createdAt,
        Instant updatedAt) {

}
