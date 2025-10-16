# bug-tracker-app


- Keywords: React Router, ...


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

## 


---

## Bug Tracking System: Roles and Responsibilities
The Bug Tracking System is designed with distinct user roles, each granted specific permissions and tools to streamline the bug resolution process.

### 🐞 Tester (Bug Reporter)
Testers are responsible for identifying, documenting, and tracking bugs.

- **Report Bugs**: Create detailed bug reports with:
  - Bug Name, ID, and Description
  - Steps to Reproduce
  - Type, Priority, and Severity
  - Environment Details (OS, Browser, Version)
- **Manage Reports**: View and track all bugs they have reported
- **Assign Bugs**: Assign newly reported bugs to specific developers
- **Attach Evidence**: Include screenshots, logs, or video recordings to demonstrate the bug
- **Verify Fixes**: Confirm when bugs are properly resolved
- **Update Status**: Change status to "Reopened" if fix is inadequate

### 👨💻 Developer (Bug Resolver)
Developers are responsible for addressing and resolving bugs assigned to them.

- **View Assignments**: Access prioritized list of all bugs assigned to them
- **Update Status**: Change bug status through resolution workflow:
  - "Investigating" → "In Progress" → "Fixed" → "Ready for Testing"
- **Add Technical Details**: Include root cause analysis and resolution notes
- **Request Clarification**: Seek additional information from testers when needed
- **Mark as "Cannot Reproduce"**: When bug cannot be replicated with provided information

### 👔 Project Manager (Bug Oversight)
Project Managers monitor bug trends and team effectiveness.

- **Bug Dashboard**: View real-time overview of all active, critical, and aging bugs
- **Priority Management**: Adjust bug priorities and reassign resources as needed
- **Trend Analysis**: Monitor bug frequency, resolution times, and recurrence rates
- **SLA Monitoring**: Track compliance with bug resolution time targets
- **Quality Metrics**: Generate reports on bug density, escape rate, and fix effectiveness

### 🦸 System Administrator (Platform Manager)
Admins maintain the bug tracking platform and standards.

- **User Management**: Create and manage tester and developer accounts
- **Workflow Configuration**: Define and maintain bug status workflows
- **Classification Setup**: Configure bug types, severity levels, and priority scales
- **Access Control**: Manage permissions and security settings
- **System Maintenance**: Ensure platform availability and performance

---

## 🧩 Modular Package Structure (Spring Modulith)

```
bug-tracker/
├── src/main/java/io/github/abbassizi/bugtracker/
│   ├── BugTrackerApplication.java
│   ├── bugs/
│   │   ├── Bug.java
│   │   ├── BugService.java
│   │   ├── BugController.java
│   │   ├── BugRepository.java
│   │   └── package-info.java
│   ├── users/
│   │   ├── User.java
│   │   ├── UserService.java
│   │   ├── UserController.java
│   │   ├── UserRepository.java
│   │   └── package-info.java
│   ├── projects/
│   │   ├── Project.java
│   │   ├── ProjectService.java
│   │   ├── ProjectController.java
│   │   ├── ProjectRepository.java
│   │   └── package-info.java
│   └── notifications/
│       ├── NotificationService.java
│       ├── NotificationListener.java
│       └── package-info.java
├── src/main/resources/
│   └── application.yml
└── src/test/java/io/github/abbassizi/bugtracker/
    ├── bugs/
    ├── users/
    ├── projects/
    └── notifications/
```





