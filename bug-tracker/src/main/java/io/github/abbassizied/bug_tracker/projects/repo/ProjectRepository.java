package io.github.abbassizied.bug_tracker.projects.repo;

import io.github.abbassizied.bug_tracker.projects.domain.Project;
import io.github.abbassizied.bug_tracker.projects.domain.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Optional<Project> findByName(String name);

    List<Project> findByOwnerId(Long ownerId);

    List<Project> findByStatus(ProjectStatus status);

    List<Project> findByOwnerIdAndStatus(Long ownerId, ProjectStatus status);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

    @Query("SELECT p FROM Project p WHERE p.ownerId = :ownerId AND (p.status = 'ACTIVE' OR p.status = 'ARCHIVED')")
    List<Project> findAllByOwnerIdWithAnyStatus(@Param("ownerId") Long ownerId);

    @Query("SELECT COUNT(p) FROM Project p WHERE p.ownerId = :ownerId")
    long countByOwnerId(@Param("ownerId") Long ownerId);
}