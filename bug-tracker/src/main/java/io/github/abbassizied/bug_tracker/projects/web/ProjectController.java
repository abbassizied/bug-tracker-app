package io.github.abbassizied.bug_tracker.projects.web;

import io.github.abbassizied.bug_tracker.projects.domain.Project;
import io.github.abbassizied.bug_tracker.projects.domain.ProjectStatus;
import io.github.abbassizied.bug_tracker.projects.dto.ProjectRequest;
import io.github.abbassizied.bug_tracker.projects.dto.ProjectResponse;
import io.github.abbassizied.bug_tracker.projects.service.ProjectService;
import io.github.abbassizied.bug_tracker.users.service.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProjectController {

    private final ProjectService projectService;

    // 🔐 Only Manager or Admin can create projects
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@Valid @RequestBody ProjectRequest projectRequest) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        Long userId = userDetails.getId(); // ✅ current logged-in user's ID

        log.info("Creating project for owner ID: {}", userId);

        Project project = Project.builder()
                .name(projectRequest.getName())
                .description(projectRequest.getDescription())
                .ownerId(userId)
                .status(projectRequest.getStatus() != null ? projectRequest.getStatus() : ProjectStatus.ACTIVE)
                .build();

        Project createdProject = projectService.createProject(project);
        ProjectResponse response = mapToResponse(createdProject);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 🔐 All authenticated users can view
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getAllProjects() {
        log.info("Fetching all projects");
        List<ProjectResponse> projects = projectService.getAllProjects().stream()
                .map(this::mapToResponse)
                .toList();
        return ResponseEntity.ok(projects);
    }

    // 🔐 All authenticated users can view
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable Long id) {
        log.info("Fetching project by ID: {}", id);
        Project project = projectService.getProjectById(id);
        return ResponseEntity.ok(mapToResponse(project));
    }

    // 🔐 Manager, Admin, or the project owner can view
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN') or #ownerId == authentication.principal.id")
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<ProjectResponse>> getProjectsByOwner(@PathVariable Long ownerId) {
        log.info("Fetching projects for owner ID: {}", ownerId);
        List<ProjectResponse> projects = projectService.getProjectsByOwner(ownerId).stream()
                .map(this::mapToResponse)
                .toList();
        return ResponseEntity.ok(projects);
    }

    // 🔐 Manager, Admin
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ProjectResponse>> getProjectsByStatus(@PathVariable ProjectStatus status) {
        log.info("Fetching projects with status: {}", status);
        List<ProjectResponse> projects = projectService.getProjectsByStatus(status).stream()
                .map(this::mapToResponse)
                .toList();
        return ResponseEntity.ok(projects);
    }

    // 🔐 Manager or Admin
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
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

    // 🔐 Only Admin
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        log.info("Deleting project with ID: {}", id);
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    // 🔐 Manager or Admin
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @PatchMapping("/{id}/archive")
    public ResponseEntity<ProjectResponse> archiveProject(@PathVariable Long id) {
        log.info("Archiving project with ID: {}", id);
        Project archivedProject = projectService.archiveProject(id);
        return ResponseEntity.ok(mapToResponse(archivedProject));
    }

    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @PatchMapping("/{id}/activate")
    public ResponseEntity<ProjectResponse> activateProject(@PathVariable Long id) {
        log.info("Activating project with ID: {}", id);
        Project activatedProject = projectService.activateProject(id);
        return ResponseEntity.ok(mapToResponse(activatedProject));
    }

    // 🔐 Manager, Admin, or Owner
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN') or #ownerId == authentication.principal.id")
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