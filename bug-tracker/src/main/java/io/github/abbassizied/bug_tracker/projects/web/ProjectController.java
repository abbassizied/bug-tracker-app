package io.github.abbassizied.bug_tracker.projects.web;

import io.github.abbassizied.bug_tracker.projects.domain.Project;
import io.github.abbassizied.bug_tracker.projects.domain.ProjectStatus;
import io.github.abbassizied.bug_tracker.projects.dto.ProjectRequest;
import io.github.abbassizied.bug_tracker.projects.dto.ProjectResponse;
import io.github.abbassizied.bug_tracker.projects.service.ProjectService;
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
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@Valid @RequestBody ProjectRequest projectRequest,
            @RequestHeader("X-User-Id") Long ownerId) {
        log.info("Creating project for owner ID: {}", ownerId);

        Project project = Project.builder()
                .name(projectRequest.getName())
                .description(projectRequest.getDescription())
                .ownerId(ownerId)
                .status(projectRequest.getStatus() != null ? projectRequest.getStatus() : ProjectStatus.ACTIVE)
                .build();

        Project createdProject = projectService.createProject(project);
        ProjectResponse response = mapToResponse(createdProject);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getAllProjects() {
        log.info("Fetching all projects");
        List<ProjectResponse> projects = projectService.getAllProjects().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable Long id) {
        log.info("Fetching project by ID: {}", id);
        Project project = projectService.getProjectById(id);
        return ResponseEntity.ok(mapToResponse(project));
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<ProjectResponse>> getProjectsByOwner(@PathVariable Long ownerId) {
        log.info("Fetching projects for owner ID: {}", ownerId);
        List<ProjectResponse> projects = projectService.getProjectsByOwner(ownerId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ProjectResponse>> getProjectsByStatus(@PathVariable ProjectStatus status) {
        log.info("Fetching projects with status: {}", status);
        List<ProjectResponse> projects = projectService.getProjectsByStatus(status).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(projects);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponse> updateProject(@PathVariable Long id,
            @Valid @RequestBody ProjectRequest projectRequest) {
        log.info("Updating project with ID: {}", id);

        Project projectDetails = Project.builder()
                .name(projectRequest.getName())
                .description(projectRequest.getDescription())
                .status(projectRequest.getStatus())
                .build();

        Project updatedProject = projectService.updateProject(id, projectDetails);
        return ResponseEntity.ok(mapToResponse(updatedProject));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        log.info("Deleting project with ID: {}", id);
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/archive")
    public ResponseEntity<ProjectResponse> archiveProject(@PathVariable Long id) {
        log.info("Archiving project with ID: {}", id);
        Project archivedProject = projectService.archiveProject(id);
        return ResponseEntity.ok(mapToResponse(archivedProject));
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<ProjectResponse> activateProject(@PathVariable Long id) {
        log.info("Activating project with ID: {}", id);
        Project activatedProject = projectService.activateProject(id);
        return ResponseEntity.ok(mapToResponse(activatedProject));
    }

    @GetMapping("/owner/{ownerId}/count")
    public ResponseEntity<Long> countProjectsByOwner(@PathVariable Long ownerId) {
        log.info("Counting projects for owner ID: {}", ownerId);
        long count = projectService.countProjectsByOwner(ownerId);
        return ResponseEntity.ok(count);
    }

    private ProjectResponse mapToResponse(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .ownerId(project.getOwnerId())
                .status(project.getStatus())
                .build();
    }
}