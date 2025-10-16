package io.github.abbassizied.bug_tracker.users.web;

import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/public")
public class PublicController {

    private static final Logger log = LoggerFactory.getLogger(PublicController.class);

    @GetMapping
    public String publicTest() {
        log.info("=== PUBLIC DEBUG ENDPOINT HIT ===");
        return "Public debug endpoint works!";
    }
}