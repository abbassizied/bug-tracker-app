package io.github.abbassizied.bug_tracker;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

class ModularityTest {

    ApplicationModules modules = ApplicationModules.of(BugTrackerApplication.class);

    @Test
    void verifyModules() {

        modules.forEach(System.out::println);
        modules.verify(); // Fails if dependencies violate boundaries
    }

}