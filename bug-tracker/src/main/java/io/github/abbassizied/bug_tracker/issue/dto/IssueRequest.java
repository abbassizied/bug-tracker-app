package io.github.abbassizied.bug_tracker.issue.dto;

import jakarta.validation.constraints.*;

public record IssueRequest(
        @NotBlank @Size(max = 255) String title,
        String description,
        Long assigneeId) {

}
