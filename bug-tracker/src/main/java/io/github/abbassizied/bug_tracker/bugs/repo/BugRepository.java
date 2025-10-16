package io.github.abbassizied.bug_tracker.bugs.repo;

import io.github.abbassizied.bug_tracker.bugs.domain.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface BugRepository extends JpaRepository<Bug, Long> {

    List<Bug> findByProjectId(Long projectId);

    List<Bug> findByReporterId(Long reporterId);

    List<Bug> findByAssigneeId(Long assigneeId);

    List<Bug> findByStatus(BugStatus status);

    List<Bug> findBySeverity(BugSeverity severity);

    List<Bug> findByProjectIdAndStatus(Long projectId, BugStatus status);

    List<Bug> findByAssigneeIdAndStatus(Long assigneeId, BugStatus status);

    @Query("SELECT b FROM Bug b WHERE b.projectId = :projectId AND b.assigneeId IS NULL")
    List<Bug> findUnassignedBugsByProject(@Param("projectId") Long projectId);

    @Query("SELECT COUNT(b) FROM Bug b WHERE b.projectId = :projectId")
    long countByProjectId(@Param("projectId") Long projectId);

    @Query("SELECT COUNT(b) FROM Bug b WHERE b.projectId = :projectId AND b.status = :status")
    long countByProjectIdAndStatus(@Param("projectId") Long projectId, @Param("status") BugStatus status);

    boolean existsByTitleAndProjectId(String title, Long projectId);
}