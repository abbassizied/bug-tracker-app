package io.github.abbassizied.bug_tracker.bugs.web;

import io.github.abbassizied.bug_tracker.bugs.domain.*;
import io.github.abbassizied.bug_tracker.bugs.dto.*;
import io.github.abbassizied.bug_tracker.bugs.service.BugService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/bugs")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class BugController {

    private final BugService bugService;

    @PostMapping
    public ResponseEntity<BugResponse> createBug(@Valid @RequestBody BugRequest bugRequest,
            @RequestHeader("X-User-Id") Long reporterId) {
        log.info("Creating bug for reporter ID: {}", reporterId);

        Bug bug = Bug.builder()
                .title(bugRequest.getTitle())
                .description(bugRequest.getDescription())
                .severity(bugRequest.getSeverity())
                .status(bugRequest.getStatus() != null ? bugRequest.getStatus() : BugStatus.OPEN)
                .reporterId(reporterId)
                .assigneeId(bugRequest.getAssigneeId())
                .projectId(bugRequest.getProjectId())
                .build();

        Bug createdBug = bugService.createBug(bug);
        BugResponse response = mapToResponse(createdBug);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<BugResponse>> getAllBugs() {
        log.info("Fetching all bugs");
        List<BugResponse> bugs = bugService.getAllBugs().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(bugs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BugResponse> getBugById(@PathVariable Long id) {
        log.info("Fetching bug by ID: {}", id);
        Bug bug = bugService.getBugById(id);
        return ResponseEntity.ok(mapToResponse(bug));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<BugResponse>> getBugsByProject(@PathVariable Long projectId) {
        log.info("Fetching bugs for project ID: {}", projectId);
        List<BugResponse> bugs = bugService.getBugsByProject(projectId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(bugs);
    }

    @GetMapping("/reporter/{reporterId}")
    public ResponseEntity<List<BugResponse>> getBugsByReporter(@PathVariable Long reporterId) {
        log.info("Fetching bugs reported by user ID: {}", reporterId);
        List<BugResponse> bugs = bugService.getBugsByReporter(reporterId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(bugs);
    }

    @GetMapping("/assignee/{assigneeId}")
    public ResponseEntity<List<BugResponse>> getBugsByAssignee(@PathVariable Long assigneeId) {
        log.info("Fetching bugs assigned to user ID: {}", assigneeId);
        List<BugResponse> bugs = bugService.getBugsByAssignee(assigneeId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(bugs);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<BugResponse>> getBugsByStatus(@PathVariable BugStatus status) {
        log.info("Fetching bugs with status: {}", status);
        List<BugResponse> bugs = bugService.getBugsByStatus(status).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(bugs);
    }

    @GetMapping("/severity/{severity}")
    public ResponseEntity<List<BugResponse>> getBugsBySeverity(@PathVariable BugSeverity severity) {
        log.info("Fetching bugs with severity: {}", severity);
        List<BugResponse> bugs = bugService.getBugsBySeverity(severity).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(bugs);
    }

    @GetMapping("/project/{projectId}/unassigned")
    public ResponseEntity<List<BugResponse>> getUnassignedBugsByProject(@PathVariable Long projectId) {
        log.info("Fetching unassigned bugs for project ID: {}", projectId);
        List<BugResponse> bugs = bugService.getUnassignedBugsByProject(projectId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(bugs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BugResponse> updateBug(@PathVariable Long id,
            @Valid @RequestBody BugRequest bugRequest) {
        log.info("Updating bug with ID: {}", id);

        Bug bugDetails = Bug.builder()
                .title(bugRequest.getTitle())
                .description(bugRequest.getDescription())
                .severity(bugRequest.getSeverity())
                .status(bugRequest.getStatus())
                .assigneeId(bugRequest.getAssigneeId())
                .projectId(bugRequest.getProjectId())
                .build();

        Bug updatedBug = bugService.updateBug(id, bugDetails);
        return ResponseEntity.ok(mapToResponse(updatedBug));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBug(@PathVariable Long id) {
        log.info("Deleting bug with ID: {}", id);
        bugService.deleteBug(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status/{status}")
    public ResponseEntity<BugResponse> updateBugStatus(@PathVariable Long id,
            @PathVariable BugStatus status) {
        log.info("Updating bug status to {} for bug ID: {}", status, id);
        Bug updatedBug = bugService.updateBugStatus(id, status);
        return ResponseEntity.ok(mapToResponse(updatedBug));
    }

    @PatchMapping("/{id}/assign/{assigneeId}")
    public ResponseEntity<BugResponse> assignBug(@PathVariable Long id,
            @PathVariable Long assigneeId) {
        log.info("Assigning bug ID: {} to developer ID: {}", id, assigneeId);
        Bug assignedBug = bugService.assignBugToDeveloper(id, assigneeId);
        return ResponseEntity.ok(mapToResponse(assignedBug));
    }

    @PatchMapping("/{id}/unassign")
    public ResponseEntity<BugResponse> unassignBug(@PathVariable Long id) {
        log.info("Unassigning bug ID: {}", id);
        Bug unassignedBug = bugService.unassignBug(id);
        return ResponseEntity.ok(mapToResponse(unassignedBug));
    }

    @GetMapping("/project/{projectId}/count")
    public ResponseEntity<Long> countBugsByProject(@PathVariable Long projectId) {
        log.info("Counting bugs for project ID: {}", projectId);
        long count = bugService.countBugsByProject(projectId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/project/{projectId}/count/status/{status}")
    public ResponseEntity<Long> countBugsByProjectAndStatus(@PathVariable Long projectId,
            @PathVariable BugStatus status) {
        log.info("Counting bugs for project ID: {} with status: {}", projectId, status);
        long count = bugService.countBugsByProjectAndStatus(projectId, status);
        return ResponseEntity.ok(count);
    }

    private BugResponse mapToResponse(Bug bug) {
        return BugResponse.builder()
                .id(bug.getId())
                .title(bug.getTitle())
                .description(bug.getDescription())
                .severity(bug.getSeverity())
                .status(bug.getStatus())
                .reporterId(bug.getReporterId())
                .assigneeId(bug.getAssigneeId())
                .projectId(bug.getProjectId())
                .build();
    }
}