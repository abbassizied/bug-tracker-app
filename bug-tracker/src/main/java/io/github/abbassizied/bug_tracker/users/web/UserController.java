package io.github.abbassizied.bug_tracker.users.web;

import io.github.abbassizied.bug_tracker.users.domain.User;
import io.github.abbassizied.bug_tracker.users.domain.ERole;
import io.github.abbassizied.bug_tracker.users.dto.UserRequest;
import io.github.abbassizied.bug_tracker.users.dto.UserResponse;
import io.github.abbassizied.bug_tracker.users.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 🔐 1. Get All Users — Admin Only
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        log.info("Fetching all users");
        List<UserResponse> users = userService.getAllUsers()
                .stream().map(this::mapToResponse).toList();
        return ResponseEntity.ok(users);
    }

    // 🔍 2. Get User by ID — All Roles (self or admin)
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','DEVELOPER','TESTER')")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        log.info("Fetching user by ID: {}", id);
        User user = userService.getUserById(id);
        return ResponseEntity.ok(mapToResponse(user));
    }

    // 🧩 3. Get Users by Role
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @GetMapping("/role/{role}")
    public ResponseEntity<List<UserResponse>> getUsersByRole(@PathVariable ERole role) {
        log.info("Fetching users by role: {}", role);
        List<UserResponse> users = userService.getUsersByRole(role)
                .stream().map(this::mapToResponse).toList();
        return ResponseEntity.ok(users);
    }

    // 🧮 4. Count Users by Role
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @GetMapping("/count/role/{role}")
    public ResponseEntity<Long> countUsersByRole(@PathVariable ERole role) {
        log.info("Counting users with role: {}", role);
        long count = userService.countUsersByRole(role);
        return ResponseEntity.ok(count);
    }

    // ✏️ 5. Update User Info (Admin or the user themself)
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','DEVELOPER','TESTER')")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id, @Valid @RequestBody UserRequest userRequest) {
        log.info("Updating user with ID: {}", id);
        User updatedUser = userService.updateUser(id, userRequest);
        return ResponseEntity.ok(mapToResponse(updatedUser));
    }

    // 🚫 6. Deactivate User — Admin only
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<UserResponse> deactivateUser(@PathVariable Long id) {
        log.info("Deactivating user with ID: {}", id);
        User deactivated = userService.deactivateUser(id);
        return ResponseEntity.ok(mapToResponse(deactivated));
    }

    // 🗑️ 7. Delete User — Admin only
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.info("Deleting user with ID: {}", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // 🧠 Mapper helper
    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(user.getRoles().stream()
                        .map(role -> {
                            String name = role.getName().name();
                            // Convert enum constant to human-friendly string
                            return switch (name) {
                                case "ROLE_ADMIN" -> "admin";
                                case "ROLE_PROJECT_MANAGER" -> "project_manager";
                                case "ROLE_DEVELOPER" -> "developer";
                                case "ROLE_TESTER" -> "tester";
                                default -> name.toLowerCase().replace("role_", "");
                            };
                        })
                        .toList())
                .active(user.isActive())
                .build();
    }

}
