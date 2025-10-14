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

##





---

## **Bug Tracking System: Roles and Responsibilities**

The Bug Tracking System is designed with distinct user roles, each granted specific permissions and tools to streamline the bug resolution process.

### **🐞 Tester (Bug Reporter)**
Testers are responsible for identifying, documenting, and tracking bugs.
*   **Report Bugs:** Create detailed bug reports with:
    *   Bug Name, ID, and Description
    *   Type, Priority, and Severity
    *   Project Name, Start Date, and Due Date
*   **Manage Reports:** View and manage all bugs they have reported.
*   **Assign Bugs:** Assign newly reported bugs to specific developers.
*   **Attach Evidence:** Include screenshots or files to support the bug report (within a 5MB size limit).
*   **Communicate:**
    *   Automatically send email notifications to developers when a bug is assigned.
    *   Initiate and participate in direct chat rooms with developers.

### **👨💻 Developer (Bug Resolver)**
Developers are responsible for addressing and resolving bugs assigned to them.
*   **View Assignments:** Access a dedicated list of all bugs assigned to them.
*   **Update Status:** Change a bug's status (e.g., "In Progress," "Fixed," "Cannot Reproduce") as work progresses.
*   **Collaborate:** Join chat rooms with testers for direct communication and clarification.
*   **Access Resources:** Search for information and access useful links to aid in bug resolution.

### **👔 Project Manager (Project Overseer)**
Project Managers monitor project health and team performance.
*   **Dashboard View:** Access a comprehensive overview of all testers, developers, and bugs in the system.
*   **Performance Analytics:** Calculate team and individual performance metrics based on:
    *   Number of bugs raised and resolved.
    *   Bug difficulty and severity levels.
*   **Reporting:** Generate and export reports on bug status, team performance, and project progress.

### **🦸 System Administrator (Platform Manager)**
Admins have full system control, focusing on user, project, and system configuration.
*   **User Management:** Create, view, update, and deactivate all user accounts.
*   **Project Management:** Create new projects and assign team members (developers & testers) to them.
*   **System Configuration:** Define and maintain standard options for the system, such as:
    *   Bug Types, Severity Levels, and Statuses.
*   **Data Maintenance:** Oversee and maintain core data, including project details, developer lists, and tester lists.

---











