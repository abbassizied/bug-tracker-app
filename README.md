# bug-tracker-app

---

## Run Modularity Verification

- After you implement all modules, rerun your modularity test:
```sh
mvn test -Dtest=ModularityTest
```
- This ensures:
    - No circular dependencies
    - Modules expose only intended APIs
    - Architecture is consistent

## JWT Secret Key

- To generate a proper Base64 secret:
```sh
# Generate a proper Base64 secret (64 characters)
openssl rand -base64 64
```

---

## 🐛 Bug Tracking System — Roles & Access Control

The **Bug Tracking System** manages software project issues across multiple teams using **modular architecture (Spring Modulith)** and **role-based security (Spring Security)**.

Each role is granted **specific privileges** that align with their real workflow and your existing REST endpoints.

### 👤 Roles Overview

### 🐞 🧪 **Tester** (Bug Reporter)

Responsible for identifying, documenting, and tracking software bugs.

**Permissions**

* Create new bug reports (`POST /api/bugs`)
* View bugs they reported (`GET /api/bugs/reporter/{id}`)
* Update status of reported bugs (e.g. “Reopened”)
* Assign bugs to developers (`PATCH /api/bugs/{id}/assign`)
* Attach additional evidence (description, logs, etc.)

**Typical Authorities**

```text
ROLE_TESTER
```

### 👨💻 **Developer** (Bug Resolver)

Handles bugs assigned to them and ensures proper resolution.

**Permissions**

* View assigned bugs (`GET /api/bugs/assignee/{id}`)
* Update bug status (e.g., “In Progress”, “Fixed”)
* Add technical details and resolution notes (`PUT /api/bugs/{id}`)
* Mark bugs as “Cannot Reproduce”

**Typical Authorities**

```text
ROLE_DEVELOPER
```

### 👔 **Project Manager** (Bug Oversight)

Oversees project quality and team productivity.

**Permissions**

* View all projects and associated bugs
  (`GET /api/projects`, `GET /api/bugs`)
* View bug trends and metrics (via reporting endpoints)
* Adjust bug priorities or reassign developers (`PATCH /api/bugs/{id}/priority`)
* Archive or activate projects (`PATCH /api/projects/{id}/archive` / `activate`)

**Typical Authorities**

```text
ROLE_MANAGER
```

### 🦸 **System Administrator** (Platform Manager)

Manages system configuration, users, and access control.

**Permissions**

* Manage users (`/api/users/**`)
* Configure roles and permissions
* Delete or archive projects
* Full visibility on all modules

**Typical Authorities**

```text
ROLE_ADMIN
```

---

## 🧩 Modular Package Structure (Spring Modulith)

- Each module can publish or listen for events, but no module directly calls another module’s service class.


##




