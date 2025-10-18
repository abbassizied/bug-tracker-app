// 📨 User Module Listeners
package io.github.abbassizied.bug_tracker.users;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

import io.github.abbassizied.bug_tracker.events.BugAssigned;
import io.github.abbassizied.bug_tracker.events.BugResolved;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserEventListener {
    private static final Logger logger = LoggerFactory.getLogger(UserEventListener.class);

    @ApplicationModuleListener
    public void onBugAssigned(BugAssigned event) {
        logger.info("User Module received BugAssigned event: {}", event);
        // Handle the event (e.g., notify the user)
    }

    @ApplicationModuleListener
    public void onBugResolved(BugResolved event) {
        logger.info("User Module received BugResolved event: {}", event);
        // Handle the event (e.g., update user stats)
    }

}