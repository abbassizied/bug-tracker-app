package io.github.abbassizied.bug_tracker.bugs.dto;

import io.github.abbassizied.bug_tracker.bugs.domain.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BugResponse {

    private Long id;
    private String title;
    private String description;
    private BugSeverity severity;
    private BugStatus status;
    private Long reporterId;
    private Long assigneeId;
    private Long projectId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}