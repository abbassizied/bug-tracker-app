package io.github.abbassizied.bug_tracker.users.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * JPA entity for User
 */
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Unique username used for login */
    @NotBlank
    @Size(min = 3, max = 50)
    @Column(nullable = false, unique = true, length = 50)
    private String username;

    /** User email (must be unique) */
    @Email
    @NotBlank
    @Size(max = 320)
    @Column(nullable = false, unique = true, length = 320)
    private String email;

    /** Encrypted password (BCrypt, Argon2, etc.) */
    @NotBlank
    @Size(min = 8, max = 255)
    @Column(nullable = false)
    @ToString.Exclude // prevent leaking passwords in logs
    private String password;

    /** User’s given name */
    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String firstName;

    /** User’s family name */
    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String lastName;

    private boolean active;

    /** Roles granted to this user */
    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    // Helper methods (DDD-style)
    public void addRole(Role role) {
        this.roles.add(role);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
    }

    /** Computed full name (not persisted) */
    @Transient
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
