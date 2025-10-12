package io.github.abbassizied.bug_tracker.user.service;

import io.github.abbassizied.bug_tracker.common.api.ApiResponse;
import io.github.abbassizied.bug_tracker.common.exception.AppException;
import io.github.abbassizied.bug_tracker.user.domain.Role;
import io.github.abbassizied.bug_tracker.user.domain.User;
import io.github.abbassizied.bug_tracker.user.dto.*;
import io.github.abbassizied.bug_tracker.user.repository.UserRepository;
import io.github.abbassizied.bug_tracker.user.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    public UserService(UserRepository repository, PasswordEncoder encoder, JwtService jwtService) {
        this.repository = repository;
        this.encoder = encoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public ApiResponse<UserResponse> register(RegisterRequest request) {
        if (repository.existsByUsername(request.username())) {
            throw new AppException("Username already taken", "USERNAME_EXISTS");
        }
        if (repository.existsByEmail(request.email())) {
            throw new AppException("Email already registered", "EMAIL_EXISTS");
        }
        var encodedPassword = encoder.encode(request.password());

        // default role USER
        User user = new User(
                null,
                request.username(),
                request.email(),
                encodedPassword,
                Role.ROLE_TESTER,
                null,
                null
        );

        User saved = repository.save(user);

        var resp = new UserResponse(
                saved.id(),
                saved.username(),
                saved.email(),
                saved.role().name(),
                saved.createdAt(),
                saved.updatedAt()
        );

        return ApiResponse.ok("User registered", resp);
    }

    @Transactional(readOnly = true)
    public ApiResponse<AuthResponse> login(LoginRequest request) {
        var userOpt = repository.findByUsername(request.username());
        if (userOpt.isEmpty()) {
            throw new AppException("Invalid credentials", "AUTH_FAILED");
        }
        var user = userOpt.get();
        if (!encoder.matches(request.password(), user.password())) {
            throw new AppException("Invalid credentials", "AUTH_FAILED");
        }

        String token = jwtService.generateToken(user.username(), user.role().name());
        return ApiResponse.ok(AuthResponse.of(token, jwtService.getExpirationSeconds()));
    }
}