package io.github.abbassizied.bug_tracker.notification.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/notifications")
public class NotificationController {

    @GetMapping
    public List<String> getAllNotifications() {
        // TODO: replace with DB or queue-based notifications
        return List.of("This is a placeholder notification API.");
    }
}