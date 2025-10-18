# 

- [Building Scalable Modular Monoliths with Spring Boot Modulith: | From Monolith to Modular](https://medium.com/@vinodjagwani/building-scalable-modular-monoliths-with-spring-boot-modulith-from-monolith-to-modular-d95989f47d2c)
- [spring-boot-modulith-fundamentals](https://github.com/kstojanovski/spring-boot-modulith-fundamentals)
- [Introduction to Spring Modulith](https://www.baeldung.com/spring-modulith)
- [A Practical Guide to Creating a Spring Modulith Project](https://dzone.com/articles/creating-a-spring-modulith-project-practical-guide)
- [Spring Modulith Doc](https://docs.spring.io/spring-modulith/reference/index.html)
- [A modular monolith application built with Spring Modulith](https://github.com/sivaprasadreddy/spring-modular-monolith)
- [spring-modulith-sample](https://github.com/vinodjagwani/spring-modulith-sample) 

---

```
cd docker-postgres-pgadmin4/
cd bug-tracker/
cd docker-mysql-pma/
```

---

- Modular Monolith is an architectural style where our source code is structured on the concept of modules.
- Spring Modulith is a project by Spring that can be used for modular monolith applications that guide developers in finding and working with application modules. 
-
- 
- Spring Modulith provides module encapsulation using sub-packages of the application module base package.
- Also, it hides types from being referred to by code residing in other packages. A module can access the content of any other module but can’t access sub-packages of other modules.
-
- 
- 
-
- 
- 
-
- 
- 
-
- 
- 







---

## 🧠 1. The fundamental rule in Spring Modulith

Spring Modulith enforces **architectural boundaries** —
✅ *Modules can depend on each other via public APIs (exposed types).*
❌ *They cannot depend on each other’s internal classes — including domain entities.*

So you **cannot** directly import an entity from another module like this:

```java
// ❌ Wrong
import io.github.abbassizi.bugtracker.user.domain.User;
```

This would violate modular boundaries.

## 🧩 2. How to handle relationships between entities

When modules need to *reference* entities from other modules, you have **3 clean options**:

### ✅ Option 1 — Reference by ID (Recommended)

Instead of holding the *entity object*, you only store its **identifier (UUID, Long, etc.)**.

Example:

```java
class Bug {
    private UUID reporterId;   // refers to User module
    private UUID projectId;    // refers to Project module
}
```

If you need the `User` or `Project` object, you ask the other module **via a service**:

```java
userService.getUserById(bug.getReporterId());
```

This is the **DDD and Modulith-friendly** approach.

### ✅ Option 2 — Use exposed DTOs from other modules

Sometimes you expose lightweight read models (DTOs) via `@ApplicationModuleExport`.

Example:
The `User` module exposes a `UserSummary` DTO (id, name, email) to others:

```java
@ApplicationModuleExport(exposedTypes = UserSummary.class)
```

Then the `Bug` module can depend on `UserSummary`, not the full entity.

### ⚠️ Option 3 — Shared kernel (last resort)

If you have *shared immutable concepts* (like enums, small value objects), you can place them in a shared module:

```
shared/
 └── domain/
      └── Priority.java
```

But never share entire JPA entities.

## 🧠 How to make them communicate

When, for example, a `Bug` is created and assigned to a `User`,
you publish a **domain event** — *not a direct reference*.

```java
@Component
@RequiredArgsConstructor
public class BugService {

    private final BugRepository bugRepository;
    private final ApplicationEventPublisher publisher;

    public Bug createBug(Bug bug) {
        bug.setCreatedAt(Instant.now());
        bug.setStatus(Bug.Status.OPEN);
        Bug saved = bugRepository.save(bug);
        publisher.publishEvent(new BugCreatedEvent(saved.getId(), saved.getProjectId(), saved.getReporterId()));
        return saved;
    }
}
```

Then in the `notifications` module:

```java
@Component
public class BugEventListener {

    private final NotificationService notificationService;

    @EventListener
    void on(BugCreatedEvent event) {
        notificationService.notifyUser(event.reporterId(), "Bug reported successfully!");
    }
}
```

## ✅ Summary

| Rule                                 | Description                       |
| ------------------------------------ | --------------------------------- |
| No entity imports between modules    | Use IDs instead                   |
| Communication                        | Domain events or exposed services |
| Each module has its own entities     | Fully encapsulated                |
| Use Lombok for brevity               | `@Getter @Setter @Builder` etc.   |
| Use UUIDs for module-safe references | Avoid foreign key coupling        |

---
 
## 🧩 Understanding How to Use Your `...Events` Classes in Spring Modulith

Spring Modulith introduces **application events between modules** — to let modules communicate **without hard dependencies**.

So the idea is:

* One module **publishes** an event (using `ApplicationEventPublisher`)
* Another module **listens** for it (using `@ApplicationModuleListener` — note: *not* `ApplicationModuleEventListener`)

✅ Your event records (`BugCreated`, `ProjectDeletedEvent`, etc.) are exactly what you need — they represent **domain events**.

### Example: Emit Event from Bugs Module

```java
package io.github.abbassizied.bug_tracker.bugs.service;

import io.github.abbassizied.bug_tracker.bugs.BugEvents;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BugServiceImpl implements BugService {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public Bug createBug(Bug bug) {
        // save bug (repository logic)
        bugRepository.save(bug);

        // 🔥 Publish domain event
        eventPublisher.publishEvent(
            new BugEvents.BugCreated(bug.getId(), bug.getProjectId(), bug.getReporterId(), bug.getTitle())
        );

        return bug;
    }
}
```
 
### Example: Listen to an Event in Another Module (e.g., Projects)

```java
package io.github.abbassizied.bug_tracker.projects.listener;

import io.github.abbassizied.bug_tracker.bugs.BugEvents;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
public class BugEventListener {

    @ApplicationModuleListener
    void onBugCreated(BugEvents.BugCreated event) {
        System.out.println("🪲 Bug created for project " + event.projectId() + " → " + event.title());
        // Example: update project stats, send notification, etc.
    }
}
```

✅ That’s the correct way to communicate across modules in **Spring Modulith**.

---