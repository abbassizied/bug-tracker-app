package io.github.abbassizied.bug_tracker.bugs.internal;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BugRepository extends JpaRepository<Bug, Long> {
    List<Bug> findByProjectId(Long projectId);

    List<Bug> findByAssigneeId(Long assigneeId);

    List<Bug> findByStatus(BugStatus status);
}