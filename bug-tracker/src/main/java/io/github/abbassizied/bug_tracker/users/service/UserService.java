package io.github.abbassizied.bug_tracker.users.service;

import io.github.abbassizied.bug_tracker.users.domain.User;
import io.github.abbassizied.bug_tracker.users.domain.Role;
import io.github.abbassizied.bug_tracker.events.UserDeactivated;
import io.github.abbassizied.bug_tracker.events.UserRegistered;
import io.github.abbassizied.bug_tracker.events.UserRoleChanged;
import io.github.abbassizied.bug_tracker.users.domain.ERole;
import io.github.abbassizied.bug_tracker.users.dto.UserRequest;
import io.github.abbassizied.bug_tracker.users.repo.RoleRepository;
import io.github.abbassizied.bug_tracker.users.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
// import org.springframework.context.annotation.Lazy;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final PasswordEncoder passwordEncoder;

    /*
     * 👇 Inject proxy of this class
     * 
     * @Lazy
     * private final UserService self;
     */
    //

    /**
     * 🧩 Register new user
     */
    public User registerUser(UserRequest userRequest) {
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new IllegalArgumentException("Username already exists: " + userRequest.getUsername());
        }

        // Convert Set<String> → Set<Role>
        Set<Role> assignedRoles = resolveRoles(userRequest.getRoles());

        User user = User.builder()
                .username(userRequest.getUsername())
                .email(userRequest.getEmail())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .roles(assignedRoles)
                .firstName("Unknown") // optional placeholder
                .lastName("User") // optional placeholder
                .build();

        User saved = userRepository.save(user);

        // Publish event: User registration
        eventPublisher.publishEvent(
                new UserRegistered(saved.getId(), saved.getUsername(),
                        saved.getRoles().stream().map(r -> r.getName().name()).toList()));

        log.info("✅ Registered new user: {} (roles: {})", saved.getUsername(),
                saved.getRoles().stream().map(r -> r.getName().name()).toList());
        return saved;
    }

    /**
     * 🧩 Utility method: convert role names to Role entities
     */
    private Set<Role> resolveRoles(Set<String> roleNames) {
        if (roleNames == null || roleNames.isEmpty()) {
            return Set.of(roleRepository.findByName(ERole.ROLE_TESTER)
                    .orElseThrow(() -> new IllegalArgumentException("Default role ROLE_TESTER not found")));
        }

        return roleNames.stream()
                .map(roleName -> {
                    try {
                        ERole eRole = ERole.valueOf(roleName);
                        return roleRepository.findByName(eRole)
                                .orElseThrow(() -> new IllegalArgumentException("Role not found: " + roleName));
                    } catch (IllegalArgumentException e) {
                        throw new IllegalArgumentException("Invalid role name: " + roleName);
                    }
                })
                .collect(Collectors.toSet());
    }

    /**
     * 📜 Get all users (Admin only)
     */
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * 🔍 Get user by ID
     */
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));
    }

    // 🧩 Get users by role
    public List<User> getUsersByRole(ERole role) {
        return userRepository.findAll().stream()
                .filter(u -> u.getRoles().stream()
                        .anyMatch(r -> r.getName() == role))
                .toList();
    }

    // 🧮 Count users by role
    public long countUsersByRole(ERole role) {
        return userRepository.findAll().stream()
                .filter(u -> u.getRoles().stream()
                        .anyMatch(r -> r.getName() == role))
                .count();
    }

    /**
     * ✏️ Update user info (Admin or self)
     */
    public User updateUser(Long id, UserRequest userRequest) {
        // ✅ Use injected proxy instead of `this`
        User existing = getUserById(id);

        if (userRequest.getUsername() != null)
            existing.setUsername(userRequest.getUsername());
        if (userRequest.getEmail() != null)
            existing.setEmail(userRequest.getEmail());
        if (userRequest.getRoles() != null && !userRequest.getRoles().isEmpty()) {
            existing.setRoles(resolveRoles(userRequest.getRoles()));
        }
        if (userRequest.getPassword() != null && !userRequest.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }

        User updated = userRepository.save(existing);
        log.info("✏️ Updated user: {}", updated.getUsername());

        eventPublisher.publishEvent(
                new UserRoleChanged(updated.getId(),
                        updated.getRoles().stream().map(r -> r.getName().name()).toList()));

        return updated;
    }

    /**
     * 🚫 Deactivate user (Admin only)
     */
    public User deactivateUser(Long id) {
        // ✅ Use injected proxy instead of `this`
        User user = getUserById(id);
        user.setActive(false);
        User deactivated = userRepository.save(user);

        eventPublisher.publishEvent(new UserDeactivated(user.getId()));
        log.info("🚫 Deactivated user ID: {}", id);
        return deactivated;
    }

    /**
     * 🗑️ Delete user (Admin only)
     */
    public void deleteUser(Long id) {
        // ✅ Use injected proxy instead of `this`
        User user = getUserById(id);
        userRepository.deleteById(id);
        eventPublisher.publishEvent(new UserDeactivated(user.getId()));
        log.info("🗑️ Deleted user ID: {}", id);
    }
}
