package io.github.abbassizied.bug_tracker.projects.service;

import io.github.abbassizied.bug_tracker.projects.domain.Project;
import io.github.abbassizied.bug_tracker.projects.domain.ProjectStatus;
import io.github.abbassizied.bug_tracker.projects.repo.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Override
    public Project createProject(Project project) {
        log.info("Creating new project: {}", project.getName());

        if (projectRepository.existsByName(project.getName())) {
            throw new IllegalArgumentException("Project with name '" + project.getName() + "' already exists");
        }

        project.setStatus(ProjectStatus.ACTIVE);
        Project savedProject = projectRepository.save(project);
        log.info("Project created successfully with ID: {}", savedProject.getId());
        return savedProject;
    }

    @Override
    public Project updateProject(Long projectId, Project projectDetails) {
        log.info("Updating project with ID: {}", projectId);

        Project existingProject = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found with ID: " + projectId));

        if (projectRepository.existsByNameAndIdNot(projectDetails.getName(), projectId)) {
            throw new IllegalArgumentException("Project with name '" + projectDetails.getName() + "' already exists");
        }

        existingProject.setName(projectDetails.getName());
        existingProject.setDescription(projectDetails.getDescription());
        existingProject.setStatus(projectDetails.getStatus());

        Project updatedProject = projectRepository.save(existingProject);
        log.info("Project updated successfully: {}", updatedProject.getName());
        return updatedProject;
    }

    @Override
    public void deleteProject(Long projectId) {
        log.info("Deleting project with ID: {}", projectId);

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found with ID: " + projectId));

        projectRepository.delete(project);
        log.info("Project deleted successfully: {}", project.getName());
    }

    @Override
    @Transactional(readOnly = true)
    public Project getProjectById(Long projectId) {
        log.debug("Fetching project by ID: {}", projectId);
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found with ID: " + projectId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> getAllProjects() {
        log.debug("Fetching all projects");
        return projectRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> getProjectsByOwner(Long ownerId) {
        log.debug("Fetching projects for owner ID: {}", ownerId);
        return projectRepository.findByOwnerId(ownerId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> getProjectsByStatus(ProjectStatus status) {
        log.debug("Fetching projects with status: {}", status);
        return projectRepository.findByStatus(status);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> getProjectsByOwnerAndStatus(Long ownerId, ProjectStatus status) {
        log.debug("Fetching projects for owner ID: {} with status: {}", ownerId, status);
        return projectRepository.findByOwnerIdAndStatus(ownerId, status);
    }

    @Override
    public Project archiveProject(Long projectId) {
        log.info("Archiving project with ID: {}", projectId);

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found with ID: " + projectId));

        project.setStatus(ProjectStatus.ARCHIVED);
        Project archivedProject = projectRepository.save(project);
        log.info("Project archived successfully: {}", archivedProject.getName());
        return archivedProject;
    }

    @Override
    public Project activateProject(Long projectId) {
        log.info("Activating project with ID: {}", projectId);

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found with ID: " + projectId));

        project.setStatus(ProjectStatus.ACTIVE);
        Project activatedProject = projectRepository.save(project);
        log.info("Project activated successfully: {}", activatedProject.getName());
        return activatedProject;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return projectRepository.existsByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public long countProjectsByOwner(Long ownerId) {
        return projectRepository.countByOwnerId(ownerId);
    }
}