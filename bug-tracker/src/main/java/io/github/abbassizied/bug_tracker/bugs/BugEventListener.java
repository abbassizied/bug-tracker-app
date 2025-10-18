// 📨 Bug Module Listeners
// Spring Modulith Event Listeners for inter-module communication
package io.github.abbassizied.bug_tracker.bugs;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

import io.github.abbassizied.bug_tracker.bugs.domain.Bug;
import io.github.abbassizied.bug_tracker.bugs.domain.BugStatus;
import io.github.abbassizied.bug_tracker.bugs.service.BugService;
import io.github.abbassizied.bug_tracker.events.ProjectArchived;
import io.github.abbassizied.bug_tracker.events.ProjectDeletedEvent;
import io.github.abbassizied.bug_tracker.events.UserDeletedEvent;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BugEventListener {

    private final BugService bugService;
    private static final Logger log = LoggerFactory.getLogger(BugEventListener.class);

    // Listen to: ProjectArchived
    @ApplicationModuleListener
    public void handleProjectArchived(ProjectArchived event) {
        // Close all open bugs when project is archived
        bugService.closeAllProjectBugs(event.projectId());
    }

    @ApplicationModuleListener
    public void onProjectDeleted(ProjectDeletedEvent event) {
        log.info("Handling project deletion for project ID: {}", event.projectId());
        List<Bug> projectBugs = bugService.findByProjectId(event.projectId());
        if (!projectBugs.isEmpty()) {
            bugService.deleteAll(projectBugs);
            log.info("Deleted {} bugs for project ID: {}", projectBugs.size(), event.projectId());
        }
    }

    @ApplicationModuleListener
    public void onUserDeleted(UserDeletedEvent event) {
        log.info("Handling user deletion for user ID: {}", event.userId());

        // Unassign bugs assigned to this user
        List<Bug> assignedBugs = bugService.getBugsByAssignee(event.userId());
        assignedBugs.forEach(bug -> {
            bug.setAssigneeId(null);
            if (bug.getStatus() == BugStatus.IN_PROGRESS) {
                bugService.updateBugStatus(bug.getId(), BugStatus.OPEN);
            }
        });
        log.info("Unassigned {} bugs for deleted user ID: {}", assignedBugs.size(), event.userId());
    }

}