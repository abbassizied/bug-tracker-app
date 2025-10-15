package io.github.abbassizied.bug_tracker.users.web;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.abbassizied.bug_tracker.users.domain.ERole;
import io.github.abbassizied.bug_tracker.users.domain.Role;
import io.github.abbassizied.bug_tracker.users.domain.User;
import io.github.abbassizied.bug_tracker.users.dto.LoginRequest;
import io.github.abbassizied.bug_tracker.users.dto.SignupRequest;
import io.github.abbassizied.bug_tracker.users.dto.JwtResponse;
import io.github.abbassizied.bug_tracker.users.dto.MessageResponse;
import io.github.abbassizied.bug_tracker.users.repo.RoleRepository;
import io.github.abbassizied.bug_tracker.users.repo.UserRepository;
import io.github.abbassizied.bug_tracker.users.jwt.JwtUtils;
import io.github.abbassizied.bug_tracker.users.service.UserDetailsImpl;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthController(
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    // -----------------------------
    // 🔐 SIGNUP
    // -----------------------------
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user
        User user = User.builder()
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .firstName(signUpRequest.getFirstName())
                .lastName(signUpRequest.getLastName())
                .build();

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null || strRoles.isEmpty()) {
            // Default role: DEVELOPER
            Role defaultRole = roleRepository.findByName(ERole.ROLE_DEVELOPER)
                    .orElseThrow(() -> new IllegalStateException("Error: Default role not found."));
            roles.add(defaultRole);
        } else {
            strRoles.forEach(roleName -> {
                ERole eRole;
                switch (roleName.toLowerCase()) {
                    case "admin" -> eRole = ERole.ROLE_ADMIN;
                    case "manager", "project_manager" -> eRole = ERole.ROLE_PROJECT_MANAGER;
                    case "tester" -> eRole = ERole.ROLE_TESTER;
                    case "developer" -> eRole = ERole.ROLE_DEVELOPER;
                    default -> throw new IllegalArgumentException("Error: Unknown role " + roleName);
                }

                Role role = roleRepository.findByName(eRole)
                        .orElseThrow(() -> new IllegalStateException("Error: Role " + eRole + " not found."));
                roles.add(role);
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    // -----------------------------
    // 🔑 SIGNIN
    // -----------------------------
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = new ArrayList<>(userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .toList());

        return ResponseEntity.ok(new JwtResponse(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

}