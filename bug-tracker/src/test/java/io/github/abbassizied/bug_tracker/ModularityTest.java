package io.github.abbassizied.bug_tracker;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

class ModularityTest {

    @Test
    void verifyModules() {
        ApplicationModules modules = ApplicationModules.of(BugTrackerApplication.class);
        modules.forEach(System.out::println);
        modules.verify(); // Fails if dependencies violate boundaries
    }

    @Test
    void generateDocumentation() {
        ApplicationModules modules = ApplicationModules.of(BugTrackerApplication.class);
        new Documenter(modules).writeDocumentation();
    }
}