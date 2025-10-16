package io.github.abbassizied.bug_tracker.bugs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.events.ApplicationModuleEventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.abbassizied.bug_tracker.bugs.domain.*;
import io.github.abbassizied.bug_tracker.bugs.repo.BugRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BugServiceImpl implements BugService {

    private final BugRepository bugRepository;

    @Override
    public Bug createBug(Bug bug) {
        log.info("Creating new bug: {}", bug.getTitle());
        
        if (bugRepository.existsByTitleAndProjectId(bug.getTitle(), bug.getProjectId())) {
            throw new IllegalArgumentException("Bug with title '" + bug.getTitle() + "' already exists in this project");
        }
        
        if (bug.getStatus() == null) {
            bug.setStatus(BugStatus.OPEN);
        }
        
        Bug savedBug = bugRepository.save(bug);
        log.info("Bug created successfully with ID: {}", savedBug.getId());
        return savedBug;
    }

    @Override
    public Bug updateBug(Long bugId, Bug bugDetails) {
        log.info("Updating bug with ID: {}", bugId);
        
        Bug existingBug = bugRepository.findById(bugId)
                .orElseThrow(() -> new IllegalArgumentException("Bug not found with ID: " + bugId));
        
        if (bugRepository.existsByTitleAndProjectId(bugDetails.getTitle(), existingBug.getProjectId()) &&
            !existingBug.getTitle().equals(bugDetails.getTitle())) {
            throw new IllegalArgumentException("Bug with title '" + bugDetails.getTitle() + "' already exists in this project");
        }
        
        existingBug.setTitle(bugDetails.getTitle());
        existingBug.setDescription(bugDetails.getDescription());
        existingBug.setSeverity(bugDetails.getSeverity());
        existingBug.setStatus(bugDetails.getStatus());
        
        Bug updatedBug = bugRepository.save(existingBug);
        log.info("Bug updated successfully: {}", updatedBug.getTitle());
        return updatedBug;
    }

    @Override
    public void deleteBug(Long bugId) {
        log.info("Deleting bug with ID: {}", bugId);
        
        Bug bug = bugRepository.findById(bugId)
                .orElseThrow(() -> new IllegalArgumentException("Bug not found with ID: " + bugId));
        
        bugRepository.delete(bug);
        log.info("Bug deleted successfully: {}", bug.getTitle());
    }

    @Override
    @Transactional(readOnly = true)
    public Bug getBugById(Long bugId) {
        log.debug("Fetching bug by ID: {}", bugId);
        return bugRepository.findById(bugId)
                .orElseThrow(() -> new IllegalArgumentException("Bug not found with ID: " + bugId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Bug> getAllBugs() {
        log.debug("Fetching all bugs");
        return bugRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Bug> getBugsByProject(Long projectId) {
        log.debug("Fetching bugs for project ID: {}", projectId);
        return bugRepository.findByProjectId(projectId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Bug> getBugsByReporter(Long reporterId) {
        log.debug("Fetching bugs reported by user ID: {}", reporterId);
        return bugRepository.findByReporterId(reporterId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Bug> getBugsByAssignee(Long assigneeId) {
        log.debug("Fetching bugs assigned to user ID: {}", assigneeId);
        return bugRepository.findByAssigneeId(assigneeId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Bug> getBugsByStatus(BugStatus status) {
        log.debug("Fetching bugs with status: {}", status);
        return bugRepository.findByStatus(status);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Bug> getBugsBySeverity(BugSeverity severity) {
        log.debug("Fetching bugs with severity: {}", severity);
        return bugRepository.findBySeverity(severity);
    }

    @Override
    public Bug updateBugStatus(Long bugId, BugStatus status) {
        log.info("Updating bug status to {} for bug ID: {}", status, bugId);
        
        Bug bug = bugRepository.findById(bugId)
                .orElseThrow(() -> new IllegalArgumentException("Bug not found with ID: " + bugId));
        
        bug.setStatus(status);
        Bug updatedBug = bugRepository.save(bug);
        log.info("Bug status updated successfully: {} -> {}", updatedBug.getTitle(), status);
        return updatedBug;
    }

    @Override
    public Bug assignBugToDeveloper(Long bugId, Long assigneeId) {
        log.info("Assigning bug ID: {} to developer ID: {}", bugId, assigneeId);
        
        Bug bug = bugRepository.findById(bugId)
                .orElseThrow(() -> new IllegalArgumentException("Bug not found with ID: " + bugId));
        
        bug.setAssigneeId(assigneeId);
        if (bug.getStatus() == BugStatus.OPEN) {
            bug.setStatus(BugStatus.IN_PROGRESS);
        }
        
        Bug assignedBug = bugRepository.save(bug);
        log.info("Bug assigned successfully: {} to developer {}", assignedBug.getTitle(), assigneeId);
        return assignedBug;
    }

    @Override
    public Bug unassignBug(Long bugId) {
        log.info("Unassigning bug ID: {}", bugId);
        
        Bug bug = bugRepository.findById(bugId)
                .orElseThrow(() -> new IllegalArgumentException("Bug not found with ID: " + bugId));
        
        bug.setAssigneeId(null);
        if (bug.getStatus() == BugStatus.IN_PROGRESS) {
            bug.setStatus(BugStatus.OPEN);
        }
        
        Bug unassignedBug = bugRepository.save(bug);
        log.info("Bug unassigned successfully: {}", unassignedBug.getTitle());
        return unassignedBug;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Bug> getUnassignedBugsByProject(Long projectId) {
        log.debug("Fetching unassigned bugs for project ID: {}", projectId);
        return bugRepository.findUnassignedBugsByProject(projectId);
    }

    @Override
    @Transactional(readOnly = true)
    public long countBugsByProject(Long projectId) {
        return bugRepository.countByProjectId(projectId);
    }

    @Override
    @Transactional(readOnly = true)
    public long countBugsByProjectAndStatus(Long projectId, BugStatus status) {
        return bugRepository.countByProjectIdAndStatus(projectId, status);
    }

    // Spring Modulith Event Listeners for inter-module communication
    @ApplicationModuleEventListener
    public void onProjectDeleted(ProjectDeletedEvent event) {
        log.info("Handling project deletion for project ID: {}", event.projectId());
        List<Bug> projectBugs = bugRepository.findByProjectId(event.projectId());
        if (!projectBugs.isEmpty()) {
            bugRepository.deleteAll(projectBugs);
            log.info("Deleted {} bugs for project ID: {}", projectBugs.size(), event.projectId());
        }
    }

    @ApplicationModuleEventListener
    public void onUserDeleted(UserDeletedEvent event) {
        log.info("Handling user deletion for user ID: {}", event.userId());
        
        // Unassign bugs assigned to this user
        List<Bug> assignedBugs = bugRepository.findByAssigneeId(event.userId());
        assignedBugs.forEach(bug -> {
            bug.setAssigneeId(null);
            if (bug.getStatus() == BugStatus.IN_PROGRESS) {
                bug.setStatus(BugStatus.OPEN);
            }
        });
        bugRepository.saveAll(assignedBugs);
        
        log.info("Unassigned {} bugs for deleted user ID: {}", assignedBugs.size(), event.userId());
    }
}