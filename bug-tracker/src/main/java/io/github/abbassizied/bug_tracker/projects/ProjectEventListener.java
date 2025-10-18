// 📨 Project Module Listeners
package io.github.abbassizied.bug_tracker.projects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

import io.github.abbassizied.bug_tracker.projects.service.ProjectService;
import io.github.abbassizied.bug_tracker.events.BugCreated;
import io.github.abbassizied.bug_tracker.events.BugResolved;
import io.github.abbassizied.bug_tracker.events.UserDeactivated;
import io.github.abbassizied.bug_tracker.events.UserRegistered;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProjectEventListener {

    private static final Logger logger = LoggerFactory.getLogger(ProjectEventListener.class);
    private final ProjectService projectService;

    @ApplicationModuleListener
    public void handleUserRegistered(UserRegistered event) {
        logger.info("Handling UserRegistered event for user: {}", event.userId());
        projectService.assignDefaultProjectsToNewUser(event.userId());
    }

    @ApplicationModuleListener
    public void handleUserDeactivated(UserDeactivated event) {
        logger.info("Handling UserDeactivated event for user: {}", event.userId());
        projectService.removeUserFromAllProjects(event.userId());
    }

    @ApplicationModuleListener
    public void handleBugCreated(BugCreated event) {
        logger.info("Handling BugCreated event for bug: {}", event.bugId());
        projectService.notifyProjectMembersOfNewBug(event.projectId(), event.bugId());
    }

    @ApplicationModuleListener
    public void handleBugResolved(BugResolved event) {
        logger.info("Handling BugResolved event for bug: {}", event.bugId());
        projectService.updateProjectStatusOnBugResolution(event.projectId(), event.bugId());
    }

}