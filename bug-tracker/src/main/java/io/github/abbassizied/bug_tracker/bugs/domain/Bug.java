package io.github.abbassizied.bug_tracker.bugs.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bugs")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bug {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private BugSeverity severity;

    @Enumerated(EnumType.STRING)
    private BugStatus status;

    // Instead of User entity references:
    private Long reporterId; // who reported the bug
    private Long assigneeId; // assigned developer
    private Long projectId; // linked project
}
