package io.github.abbassizied.bug_tracker.users.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.abbassizied.bug_tracker.users.domain.ERole;
import io.github.abbassizied.bug_tracker.users.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    List<User> findByRole(ERole role);

    long countByRole(ERole role);
}