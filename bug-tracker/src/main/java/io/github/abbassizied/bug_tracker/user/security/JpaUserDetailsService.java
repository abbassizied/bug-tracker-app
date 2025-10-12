package io.github.abbassizied.bug_tracker.user.security;

import io.github.abbassizied.bug_tracker.user.domain.User;
import io.github.abbassizied.bug_tracker.user.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    public JpaUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(
                user.username(),
                user.password(),
                java.util.List.of(new SimpleGrantedAuthority(user.role().name()))
        );
    }
}