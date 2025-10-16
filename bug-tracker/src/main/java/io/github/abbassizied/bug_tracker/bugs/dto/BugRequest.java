package io.github.abbassizied.bug_tracker.bugs.dto;

import io.github.abbassizied.bug_tracker.bugs.domain.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BugRequest {

    @NotBlank(message = "Bug title is required")
    @Size(min = 3, max = 100, message = "Bug title must be between 3 and 100 characters")
    private String title;

    @NotBlank(message = "Bug description is required")
    @Size(min = 10, max = 2000, message = "Bug description must be between 10 and 2000 characters")
    private String description;

    @NotNull(message = "Bug severity is required")
    private BugSeverity severity;

    private BugStatus status;

    @NotNull(message = "Project ID is required")
    private Long projectId;

    private Long assigneeId;
}