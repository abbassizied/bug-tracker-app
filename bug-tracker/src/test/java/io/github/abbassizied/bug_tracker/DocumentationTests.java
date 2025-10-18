package io.github.abbassizied.bug_tracker;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

import static org.assertj.core.api.Assertions.assertThat;

class DocumentationTests {

    ApplicationModules modules = ApplicationModules.of(BugTrackerApplication.class);

    @Test
    void writeDocumentationSnippets() {
        // Act
        var documenter = new Documenter(modules);
        var result = documenter
                .writeDocumentation()
                .writeModulesAsPlantUml()
                .writeIndividualModulesAsPlantUml();

        // Assert - Verify the documentation was generated successfully
        assertThat(result).isNotNull();
        assertThat(modules).isNotNull().hasSizeGreaterThan(0); // Verify we have modules to document

        // Optional: Verify specific modules exist
        assertThat(modules.getModuleByName("users")).isPresent();
        assertThat(modules.getModuleByName("projects")).isPresent();
        assertThat(modules.getModuleByName("bugs")).isPresent();
    }
}