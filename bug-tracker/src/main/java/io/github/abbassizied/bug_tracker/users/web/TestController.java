package io.github.abbassizied.bug_tracker.users.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('DEVELOPER') or hasRole('TESTER') or hasRole('PROJECT_MANAGER') or hasRole('ADMIN')")
    public String userAccess() {
        return "User Content.";
    }

    @GetMapping("/project-manager")
    @PreAuthorize("hasRole('PROJECT_MANAGER')")
    public String projectManagerAccess() {
        return "Project Manager Board.";
    }

    @GetMapping("/developer")
    @PreAuthorize("hasRole('DEVELOPER')")
    public String developerAccess() {
        return "Developer Board.";
    }

    @GetMapping("/tester")
    @PreAuthorize("hasRole('TESTER')")
    public String testerAccess() {
        return "Tester Board.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }
}
