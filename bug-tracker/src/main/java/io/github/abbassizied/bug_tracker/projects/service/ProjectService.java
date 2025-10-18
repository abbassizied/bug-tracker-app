package io.github.abbassizied.bug_tracker.projects.service;

import io.github.abbassizied.bug_tracker.projects.domain.Project;
import io.github.abbassizied.bug_tracker.projects.domain.ProjectStatus;

import java.util.List;

public interface ProjectService {

    Project createProject(Project project);

    Project updateProject(Long projectId, Project projectDetails);

    void deleteProject(Long projectId);

    Project getProjectById(Long projectId);

    List<Project> getAllProjects();

    List<Project> getProjectsByOwner(Long ownerId);

    List<Project> getProjectsByStatus(ProjectStatus status);

    List<Project> getProjectsByOwnerAndStatus(Long ownerId, ProjectStatus status);

    Project archiveProject(Long projectId);

    Project activateProject(Long projectId);

    boolean existsByName(String name);

    long countProjectsByOwner(Long ownerId);

    void assignDefaultProjectsToNewUser(Long userId);

    void removeUserFromAllProjects(Long userId);

    void notifyProjectMembersOfNewBug(Long projectId, Long bugId);

    void updateProjectStatusOnBugResolution(Long projectId, Long bugId);
}