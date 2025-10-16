# Bug Tracker API

---

## Base URL

```
[http://localhost:8082/api](http://localhost:8082/api)
````

---

## Authentication & Role-Based Access Test

- This section describes all the endpoints and tests needed to validate user registration, login, and role-based access for the Bug Tracker application.

### 1. User Registration (Sign Up)

- **Tests**:

* ✅ **Successful registration** with a valid role.
* ❌ **Duplicate username** should return error.
* ❌ **Duplicate email** should return error.
* ❌ **Invalid email format** should return validation error.
* ❌ **Password < 6 characters** should return validation error.
* ❌ **Empty required fields** should return validation errors.

**Endpoint:** ```Post http://localhost:8082/api/auth/signup``` 
**Request Body**
```json
{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "securePassword123",
  "firstName": "John",
  "lastName": "Doe",
  "role": ["developer"]
}
````

```json
{
  "username": "abbassi_zied",
  "email": "abbassizied@outlook.fr",
  "password": "securePassword123",
  "firstName": "Zied",
  "lastName": "Abbassi",
  "role": ["admin", "project_manager"]
}
````

```json
{
  "username": "admin",
  "email": "admin@local.tn",
  "password": "admin123",
  "firstName": "admin",
  "lastName": "admin",
  "role": ["admin", "project_manager", "tester", "developer"]
}
````

**Response**

```json
{
    "message": "User registered successfully!"
}
```

### 2. User Login (Sign In)

**Tests** 
* ✅ **Successful login** returns JWT and user info.
* ❌ **Wrong password** returns 401 Unauthorized.
* ❌ **Nonexistent username** returns 401 Unauthorized.

**Endpoint:** ```POST http://localhost:8082/api/auth/signin```
**Description:** Authenticate a user and receive JWT token.
**Request Body:**

```json
{
  "username": "john_doe",
  "password": "securePassword123"
}
```

**Response**

```json
{
  "token": "<JWT_TOKEN>",
  "type": "Bearer",
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "roles": ["DEVELOPER"]
}
```

- Example:
```
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huX2RvZSIsImlhdCI6MTc2MDYwMjA0OCwiZXhwIjoxNzYwNjg4NDQ4fQ.5-6VzVMtpKC_L7BbRT68TBLK3yQO9nBzwpaU-Sr-IfE",
    "id": 1,
    "username": "john_doe",
    "email": "john@example.com",
    "roles": [
        "ROLE_DEVELOPER"
    ],
    "type": "Bearer"
}
```

### 3. Public Endpoint


**Tests:**
* ✅ Access without token returns `"Public Content."`
* ✅ Access with token returns `"Public Content."`

**Endpoint:** `GET http://localhost:8082/api/public`
**Description:** Accessible by anyone without authentication.

### 4. Role-Based Endpoints

All endpoints below require **JWT authentication**.

| Endpoint                    | Allowed Roles                             | Test Case                                                       |
| --------------------------- | ----------------------------------------- | --------------------------------------------------------------- |
| `GET http://localhost:8082/api/secured/all`            | DEVELOPER, TESTER, PROJECT_MANAGER, ADMIN | ✅ Access with any allowed role; ❌ Access with unauthorized role |
| `GET http://localhost:8082/api/secured/developer`       | DEVELOPER                                 | ✅ Access with DEVELOPER; ❌ Others                               |
| `GET http://localhost:8082/api/secured/tester`          | TESTER                                    | ✅ Access with TESTER; ❌ Others                                  |
| `GET http://localhost:8082/api/secured/project-manager` | PROJECT_MANAGER                           | ✅ Access with PROJECT_MANAGER; ❌ Others                         |
| `GET http://localhost:8082/api/secured/admin`           | ADMIN                                     | ✅ Access with ADMIN; ❌ Others                                   |

**Example Request (with JWT)**

```http
GET /test/admin
Authorization: Bearer <JWT_TOKEN>
```

**Expected Response**

* ✅ Authorized: `"Admin Board."`
* ❌ Unauthorized: `401 Unauthorized` or `"Vous devez vous connecter"`

---

## Project Endpoints


** 🌱 1. Create Projects (4 different examples)

```bash
# Project 1
curl -X POST http://localhost:8082/api/projects \
-H "Content-Type: application/json" \
-H "X-User-Id: 101" \
-d '{
  "name": "Bug Tracker Platform",
  "description": "A collaborative bug tracking system for development teams.",
  "status": "ACTIVE"
}'

# Project 2
curl -X POST http://localhost:8082/api/projects \
-H "Content-Type: application/json" \
-H "X-User-Id: 102" \
-d '{
  "name": "AI Issue Classifier",
  "description": "Machine learning service that auto-classifies bug reports.",
  "status": "ACTIVE"
}'

# Project 3
curl -X POST http://localhost:8082/api/projects \
-H "Content-Type: application/json" \
-H "X-User-Id: 101" \
-d '{
  "name": "Frontend Redesign",
  "description": "Revamping the UI using Angular 19 and PrimeNG.",
  "status": "ACTIVE"
}'

# Project 4
curl -X POST http://localhost:8082/api/projects \
-H "Content-Type: application/json" \
-H "X-User-Id: 103" \
-d '{
  "name": "Cloud Migration Initiative",
  "description": "Moving legacy modules to Kubernetes-based microservices.",
  "status": "ARCHIVED"
}'
```

** 📜 2. Get All Projects

```bash
curl -X GET http://localhost:8082/api/projects
```

** 🔍 3. Get Project by ID

```bash
curl -X GET http://localhost:8082/api/projects/1
```

** 👤 4. Get Projects by Owner

```bash
curl -X GET http://localhost:8082/api/projects/owner/1
```

** ⚙️ 5. Get Projects by Status

```bash
# Get all ACTIVE projects
curl -X GET http://localhost:8082/api/projects/status/ACTIVE

# Get all ARCHIVED projects
curl -X GET http://localhost:8082/api/projects/status/ARCHIVED
```
 
** 🔢 6. Get Projects Count by Owner

```bash
curl -X GET http://localhost:8082/api/projects/owner/1/count
```
 
** ✏️ 7. Update Project

```bash
curl -X PUT http://localhost:8082/api/projects/2 \
-H "Content-Type: application/json" \
-d '{
  "name": "AI Issue Classifier v2",
  "description": "Upgraded ML model for better bug classification accuracy.",
  "status": "ACTIVE"
}'
```
 
** ✅ 8. Activate Project

```bash
curl -X PATCH http://localhost:8082/api/projects/4/activate
```

** 🗑️ 9. Delete Project

```bash
curl -X DELETE http://localhost:8082/api/projects/3
```

---

## Bug Endpoints

** 🐞 1. Create Bugs (4 examples)

```bash
# Bug 1 - UI not responsive on mobile
curl -X POST http://localhost:8082/api/bugs \
-H "Content-Type: application/json" \
-H "X-User-Id: 101" \
-d '{
  "title": "UI not responsive on mobile",
  "description": "The layout of the dashboard page breaks on smaller screens below 600px width.",
  "severity": "MEDIUM",
  "status": "OPEN",
  "projectId": 1,
  "assigneeId": 201
}'

# Bug 2 - Login endpoint returns 500
curl -X POST http://localhost:8082/api/bugs \
-H "Content-Type: application/json" \
-H "X-User-Id: 102" \
-d '{
  "title": "Login endpoint returns 500",
  "description": "Attempting to log in with valid credentials triggers an internal server error.",
  "severity": "HIGH",
  "status": "OPEN",
  "projectId": 1
}'

# Bug 3 - Memory leak in AI classifier
curl -X POST http://localhost:8082/api/bugs \
-H "Content-Type: application/json" \
-H "X-User-Id: 103" \
-d '{
  "title": "Memory leak in AI classifier",
  "description": "Long-running inference tasks consume increasing memory over time until process crash.",
  "severity": "CRITICAL",
  "status": "IN_PROGRESS",
  "projectId": 2,
  "assigneeId": 202
}'

# Bug 4 - Notification email not sent
curl -X POST http://localhost:8082/api/bugs \
-H "Content-Type: application/json" \
-H "X-User-Id: 104" \
-d '{
  "title": "Notification email not sent",
  "description": "When a bug is assigned, the notification email is not delivered to the developer.",
  "severity": "LOW",
  "status": "OPEN",
  "projectId": 1
}'
```
 
** 📜 2. Get All Bugs

```bash
curl -X GET http://localhost:8082/api/bugs
```
 
** 🔍 3. Get Bug by ID

```bash
curl -X GET http://localhost:8082/api/bugs/1
```
 
** 🧩 4. Get Bugs by Project

```bash
curl -X GET http://localhost:8082/api/bugs/project/1
```
 
** 👤 5. Get Bugs by Reporter

```bash
curl -X GET http://localhost:8082/api/bugs/reporter/101
```
 
** 👨 6. Get Bugs by Assignee

```bash
curl -X GET http://localhost:8082/api/bugs/assignee/201
```
 
** ⚙️ 7. Get Bugs by Status

```bash
# Get all OPEN bugs
curl -X GET http://localhost:8082/api/bugs/status/OPEN

# Get all IN_PROGRESS bugs
curl -X GET http://localhost:8082/api/bugs/status/IN_PROGRESS
```
 
** 🚨 8. Get Bugs by Severity

```bash
curl -X GET http://localhost:8082/api/bugs/severity/HIGH
```
 
** 🕵️ 9. Get Unassigned Bugs by Project

```bash
curl -X GET http://localhost:8082/api/bugs/project/1/unassigned
```

** ✏️ 10. Update Bug

```bash
curl -X PUT http://localhost:8082/api/bugs/2 \
-H "Content-Type: application/json" \
-d '{
  "title": "Login endpoint returns 500 (fixed stack trace)",
  "description": "Root cause identified: null pointer in UserService. Fixed in branch hotfix/login-nullptr.",
  "severity": "HIGH",
  "status": "IN_PROGRESS",
  "projectId": 1,
  "assigneeId": 202
}'
```

** 🔄 11. Update Bug Status

```bash
# Mark bug as RESOLVED
curl -X PATCH http://localhost:8082/api/bugs/2/status/RESOLVED
```

** 👷 12. Assign Bug to Developer

```bash
curl -X PATCH http://localhost:8082/api/bugs/4/assign/203
```

** 🧹 13. Unassign Bug

```bash
curl -X PATCH http://localhost:8082/api/bugs/3/unassign
```

** 🧮 14. Count Bugs by Project

```bash
curl -X GET http://localhost:8082/api/bugs/project/1/count
```

** 📊 15. Count Bugs by Project and Status

```bash
curl -X GET http://localhost:8082/api/bugs/project/1/count/status/OPEN
```

** 🗑️ 16. Delete Bug

```bash
curl -X DELETE http://localhost:8082/api/bugs/4
```

---

