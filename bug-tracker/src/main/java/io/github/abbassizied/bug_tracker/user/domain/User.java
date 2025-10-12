package io.github.abbassizied.bug_tracker.user.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.Instant;

/**
 * JPA entity modeled as a Java record.
 *
 * NOTE: Records as entities have limitations (no inheritance).
 */
@Entity
@Table(name = "users")
public record User(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id,

        @Column(nullable = false, unique = true, length = 50) String username,

        @Column(nullable = false, unique = true, length = 320) String email,

        @Column(nullable = false) String password,

        @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) Role role,

        @CreationTimestamp @Column(nullable = false, updatable = false) Instant createdAt,

        @UpdateTimestamp @Column(nullable = false) Instant updatedAt) implements Serializable {
}