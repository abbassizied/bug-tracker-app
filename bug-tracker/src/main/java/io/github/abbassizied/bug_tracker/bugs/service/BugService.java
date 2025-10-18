package io.github.abbassizied.bug_tracker.bugs.service;

import io.github.abbassizied.bug_tracker.bugs.domain.*;
import java.util.List;

public interface BugService {

    Bug createBug(Bug bug);

    Bug updateBug(Long bugId, Bug bugDetails);

    void deleteBug(Long bugId);

    Bug getBugById(Long bugId);

    List<Bug> getAllBugs();

    List<Bug> getBugsByProject(Long projectId);

    List<Bug> getBugsByReporter(Long reporterId);

    List<Bug> getBugsByAssignee(Long assigneeId);

    List<Bug> getBugsByStatus(BugStatus status);

    List<Bug> getBugsBySeverity(BugSeverity severity);

    Bug updateBugStatus(Long bugId, BugStatus status);

    Bug assignBugToDeveloper(Long bugId, Long assigneeId);

    Bug unassignBug(Long bugId);

    List<Bug> getUnassignedBugsByProject(Long projectId);

    long countBugsByProject(Long projectId);

    long countBugsByProjectAndStatus(Long projectId, BugStatus status);

    void closeAllProjectBugs(Long projectId);

    List<Bug> findByProjectId(Long projectId);
    
    void deleteAll(List<Bug> bugs);
}