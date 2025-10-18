# Bug Tracker API

---

## **Bug Tracker API - Complete Endpoint Summary**

- This table provides a comprehensive overview of all API endpoints with their respective access controls for complete role-based authorization testing.

| Endpoint | Method | Tester | Developer | Project Manager | Admin | Description |
|----------|--------|:------:|:---------:|:---------------:|:-----:|-------------|
| **AUTH ENDPOINTS** | | | | | | |
| `/api/auth/signup` | POST | ✅ | ✅ | ✅ | ✅ | User registration |
| `/api/auth/signin` | POST | ✅ | ✅ | ✅ | ✅ | User login |
| `/api/public` | GET | ✅ | ✅ | ✅ | ✅ | Public content |
| **SECURED ENDPOINTS** | | | | | | |
| `/api/secured/all` | GET | ✅ | ✅ | ✅ | ✅ | All authenticated users |
| `/api/secured/developer` | GET | ❌ | ✅ | ❌ | ❌ | Developer only |
| `/api/secured/tester` | GET | ✅ | ❌ | ❌ | ❌ | Tester only |
| `/api/secured/project-manager` | GET | ❌ | ❌ | ✅ | ❌ | Project Manager only |
| `/api/secured/admin` | GET | ❌ | ❌ | ❌ | ✅ | Admin only |
| **PROJECT ENDPOINTS** | | | | | | |
| `/api/projects` | POST | ❌ | ❌ | ✅ | ✅ | Create project |
| `/api/projects` | GET | ✅ | ✅ | ✅ | ✅ | Get all projects |
| `/api/projects/{id}` | GET | ✅ | ✅ | ✅ | ✅ | Get project by ID |
| `/api/projects/owner/{ownerId}` | GET | ❌ | ❌ | ✅ | ✅ | Get projects by owner |
| `/api/projects/status/{status}` | GET | ✅ | ✅ | ✅ | ✅ | Get projects by status |
| `/api/projects/owner/{ownerId}/count` | GET | ❌ | ❌ | ✅ | ✅ | Count projects by owner |
| `/api/projects/{id}` | PUT | ❌ | ❌ | ✅ | ✅ | Update project |
| `/api/projects/{id}/activate` | PATCH | ❌ | ❌ | ✅ | ✅ | Activate project |
| `/api/projects/{id}/archive` | PATCH | ❌ | ❌ | ✅ | ✅ | Archive project |
| `/api/projects/{id}` | DELETE | ❌ | ❌ | ❌ | ✅ | Delete project |
| **BUG ENDPOINTS** | | | | | | |
| `/api/bugs` | POST | ✅ | ❌ | ❌ | ❌ | Create bug (Tester only) |
| `/api/bugs` | GET | ❌ | ❌ | ✅ | ✅ | Get all bugs |
| `/api/bugs/{id}` | GET | ✅ | ✅ | ✅ | ✅ | Get bug by ID |
| `/api/bugs/project/{projectId}` | GET | ❌ | ❌ | ✅ | ✅ | Get bugs by project |
| `/api/bugs/reporter/{reporterId}` | GET | ✅* | ❌ | ✅ | ✅ | Get bugs by reporter |
| `/api/bugs/assignee/{assigneeId}` | GET | ❌ | ✅* | ✅ | ✅ | Get bugs by assignee |
| `/api/bugs/status/{status}` | GET | ✅ | ✅ | ✅ | ✅ | Get bugs by status |
| `/api/bugs/severity/{severity}` | GET | ❌ | ❌ | ✅ | ✅ | Get bugs by severity |
| `/api/bugs/project/{projectId}/unassigned` | GET | ❌ | ❌ | ✅ | ✅ | Get unassigned bugs |
| `/api/bugs/{id}` | PUT | ✅* | ❌ | ✅ | ✅ | Update bug |
| `/api/bugs/{id}/status/{status}` | PATCH | ✅* | ✅* | ✅ | ✅ | Update bug status |
| `/api/bugs/{id}/assign/{assigneeId}` | PATCH | ❌ | ❌ | ✅ | ✅ | Assign bug to developer |
| `/api/bugs/{id}/unassign` | PATCH | ❌ | ❌ | ✅ | ✅ | Unassign bug |
| `/api/bugs/project/{projectId}/count` | GET | ❌ | ❌ | ✅ | ✅ | Count bugs by project |
| `/api/bugs/project/{projectId}/count/status/{status}` | GET | ❌ | ❌ | ✅ | ✅ | Count bugs by project & status |
| `/api/bugs/{id}` | DELETE | ❌ | ❌ | ❌ | ✅ | Delete bug |
| **USER MANAGEMENT ENDPOINTS** | | | | | | |
| `/api/users` | GET | ❌ | ❌ | ❌ | ✅ | Get all users |
| `/api/users/{id}` | GET | ✅* | ✅* | ✅* | ✅ | Get user by ID |
| `/api/users/role/{role}` | GET | ❌ | ❌ | ✅ | ✅ | Get users by role |
| `/api/users/count/role/{role}` | GET | ❌ | ❌ | ✅ | ✅ | Count users by role |
| `/api/users/{id}` | PUT | ✅* | ✅* | ✅* | ✅ | Update user info |
| `/api/users/{id}/deactivate` | PATCH | ❌ | ❌ | ❌ | ✅ | Deactivate user |
| `/api/users/{id}` | DELETE | ❌ | ❌ | ❌ | ✅ | Delete user |

### **Legend & Notes:**

- ✅ = **Allowed** for this role
- ❌ = **Not allowed** for this role
- ✅* = **Conditional access**:
  - **Tester**: Can only access their own reported bugs
  - **Developer**: Can only access their own assigned bugs and update status
  - **All users**: Can only access/update their own user profile (unless Admin)

### **Key Security Patterns:**

1. **Admin**: Full system access
2. **Project Manager**: Project management + user/team oversight
3. **Developer**: Work on assigned bugs + project visibility
4. **Tester**: Report bugs + verify fixes + project visibility
5. **Self-service**: Users can access/update their own data
6. **Public**: Authentication endpoints accessible to all

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

- **Endpoint:** ```Post http://localhost:8082/api/auth/signup``` 
- **Request Body**
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

- **Response**

```json
{
    "message": "User registered successfully!"
}
```

### 2. User Login (Sign In)

- **Tests** 
* ✅ **Successful login** returns JWT and user info.
* ❌ **Wrong password** returns 401 Unauthorized.
* ❌ **Nonexistent username** returns 401 Unauthorized.

- **Endpoint:** ```POST http://localhost:8082/api/auth/signin```
- **Description:** Authenticate a user and receive JWT token.
- **Request Body:**

```json
{
  "username": "admin",
  "password": "admin123"
}
```

- **Response**

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

- **Tests:**
* ✅ Access without token returns `"Public Content."`
* ✅ Access with token returns `"Public Content."`

- **Endpoint:** `GET http://localhost:8082/api/public`
- **Description:** Accessible by anyone without authentication.

### 4. Role-Based Endpoints

- All endpoints below require **JWT authentication**.

| Endpoint                    | Allowed Roles                             | Test Case                                                       |
| --------------------------- | ----------------------------------------- | --------------------------------------------------------------- |
| `GET http://localhost:8082/api/secured/all`            | DEVELOPER, TESTER, PROJECT_MANAGER, ADMIN | ✅ Access with any allowed role; ❌ Access with unauthorized role |
| `GET http://localhost:8082/api/secured/developer`       | DEVELOPER                                 | ✅ Access with DEVELOPER; ❌ Others                               |
| `GET http://localhost:8082/api/secured/tester`          | TESTER                                    | ✅ Access with TESTER; ❌ Others                                  |
| `GET http://localhost:8082/api/secured/project-manager` | PROJECT_MANAGER                           | ✅ Access with PROJECT_MANAGER; ❌ Others                         |
| `GET http://localhost:8082/api/secured/admin`           | ADMIN                                     | ✅ Access with ADMIN; ❌ Others                                   |

- **Example Request (with JWT)**

```http
GET /test/admin
Authorization: Bearer <JWT_TOKEN>
```

- **Expected Response**

* ✅ Authorized: `"Admin Board."`
* ❌ Unauthorized: `401 Unauthorized` or `"Vous devez vous connecter"`

---

## Project Endpoints

### **🌱 1. Create Projects**

> Only `ROLE_PROJECT_MANAGER` and `ROLE_ADMIN` can create new projects.

```bash
# Project 1 - Bug Tracker Platform
curl -X POST http://localhost:8082/api/projects \
-H "Content-Type: application/json" \
-H "Authorization: Bearer <MANAGER_TOKEN>" \
-d '{
  "name": "Bug Tracker Platform",
  "description": "Collaborative bug tracking system for dev teams.",
  "status": "ACTIVE"
}'

# Project 2 - AI Issue Classifier
curl -X POST http://localhost:8082/api/projects \
-H "Content-Type: application/json" \
-H "Authorization: Bearer <ADMIN_TOKEN>" \
-d '{
  "name": "AI Issue Classifier",
  "description": "Machine learning service that auto-classifies bug reports.",
  "status": "ACTIVE"
}'

# Project 3 - Frontend Redesign
curl -X POST http://localhost:8082/api/projects \
-H "Content-Type: application/json" \
-H "Authorization: Bearer <MANAGER_TOKEN>" \
-d '{
  "name": "Frontend Redesign",
  "description": "Revamping UI with Angular 19 and PrimeNG.",
  "status": "ACTIVE"
}'

# Project 4 - Cloud Migration Initiative
curl -X POST http://localhost:8082/api/projects \
-H "Content-Type: application/json" \
-H "Authorization: Bearer <ADMIN_TOKEN>" \
-d '{
  "name": "Cloud Migration Initiative",
  "description": "Migrating legacy modules to Kubernetes microservices.",
  "status": "ARCHIVED"
}'
```

### **📜 2. Get All Projects**

> Visible to all authenticated users.

```bash
curl -X GET http://localhost:8082/api/projects \
-H "Authorization: Bearer <ANY_ROLE_TOKEN>"
```

### **🔍 3. Get Project by ID**

> Accessible to all roles (Tester, Developer, Manager, Admin).

```bash
curl -X GET http://localhost:8082/api/projects/1 \
-H "Authorization: Bearer <ANY_ROLE_TOKEN>"
```

### **👤 4. Get Projects by Owner**

> Only `ROLE_PROJECT_MANAGER` and `ROLE_ADMIN` can view by owner.

```bash
curl -X GET http://localhost:8082/api/projects/owner/3 \
-H "Authorization: Bearer <MANAGER_OR_ADMIN_TOKEN>"
```

### **⚙️ 5. Get Projects by Status**

> Accessible to all roles.

```bash
# Get all ACTIVE projects
curl -X GET http://localhost:8082/api/projects/status/ACTIVE \
-H "Authorization: Bearer <ANY_ROLE_TOKEN>"

# Get all ARCHIVED projects
curl -X GET http://localhost:8082/api/projects/status/ARCHIVED \
-H "Authorization: Bearer <ANY_ROLE_TOKEN>"
```

### **🔢 6. Get Projects Count by Owner**

> `ROLE_PROJECT_MANAGER` or `ROLE_ADMIN`

```bash
curl -X GET http://localhost:8082/api/projects/owner/3/count \
-H "Authorization: Bearer <MANAGER_OR_ADMIN_TOKEN>"
```

### **✏️ 7. Update Project**

> Only `ROLE_PROJECT_MANAGER` or `ROLE_ADMIN`

```bash
curl -X PUT http://localhost:8082/api/projects/2 \
-H "Content-Type: application/json" \
-H "Authorization: Bearer <MANAGER_TOKEN>" \
-d '{
  "name": "AI Issue Classifier v2",
  "description": "Upgraded ML model for higher classification accuracy.",
  "status": "ACTIVE"
}'
```

### **✅ 8. Activate Project**

> Only `ROLE_PROJECT_MANAGER` or `ROLE_ADMIN`

```bash
curl -X PATCH http://localhost:8082/api/projects/4/activate \
-H "Authorization: Bearer <MANAGER_TOKEN>"
```

### **🗑️ 9. Delete Project**

> Only `ROLE_ADMIN`

```bash
curl -X DELETE http://localhost:8082/api/projects/3 \
-H "Authorization: Bearer <ADMIN_TOKEN>"
```

---

## Bug Endpoints

### **🐞 1. Create Bugs (Tester only)**

> Only users with role `ROLE_TESTER` can report new bugs.

```bash
# Bug 1 - UI not responsive on mobile
curl -X POST http://localhost:8082/api/bugs \
-H "Content-Type: application/json" \
-H "Authorization: Bearer <TESTER_TOKEN>" \
-d '{
  "title": "UI not responsive on mobile",
  "description": "Dashboard layout breaks below 600px width.",
  "severity": "MEDIUM",
  "status": "OPEN",
  "projectId": 1,
  "assigneeId": 201
}'
```

### **📜 2. Get All Bugs**

> Accessible to `ROLE_PROJECT_MANAGER` and `ROLE_ADMIN`

```bash
curl -X GET http://localhost:8082/api/bugs \
-H "Authorization: Bearer <MANAGER_OR_ADMIN_TOKEN>"
```

### **🔍 3. Get Bug by ID**

> Accessible to all roles (Tester, Developer, Manager, Admin)

```bash
curl -X GET http://localhost:8082/api/bugs/1 \
-H "Authorization: Bearer <ANY_ROLE_TOKEN>"
```

### **🧩 4. Get Bugs by Project**

> `ROLE_PROJECT_MANAGER` or `ROLE_ADMIN`

```bash
curl -X GET http://localhost:8082/api/bugs/project/1 \
-H "Authorization: Bearer <MANAGER_OR_ADMIN_TOKEN>"
```

### **👤 5. Get Bugs by Reporter**

> `ROLE_TESTER` (self) or `ROLE_PROJECT_MANAGER` / `ROLE_ADMIN`

```bash
curl -X GET http://localhost:8082/api/bugs/reporter/3 \
-H "Authorization: Bearer <TESTER_OR_MANAGER_TOKEN>"
```

### **👨 6. Get Bugs by Assignee**

> `ROLE_DEVELOPER` (self) or `ROLE_PROJECT_MANAGER` / `ROLE_ADMIN`

```bash
curl -X GET http://localhost:8082/api/bugs/assignee/3 \
-H "Authorization: Bearer <DEV_OR_MANAGER_TOKEN>"
```

### **⚙️ 7. Get Bugs by Status**

> All roles can view bugs by status.

```bash
curl -X GET http://localhost:8082/api/bugs/status/OPEN \
-H "Authorization: Bearer <ANY_ROLE_TOKEN>"
```

### **🚨 8. Get Bugs by Severity**

> Accessible to `ROLE_PROJECT_MANAGER` or `ROLE_ADMIN`

```bash
curl -X GET http://localhost:8082/api/bugs/severity/MEDIUM \
-H "Authorization: Bearer <MANAGER_OR_ADMIN_TOKEN>"
```

### **🕵️ 9. Get Unassigned Bugs by Project**

> `ROLE_PROJECT_MANAGER` can view to assign bugs.

```bash
curl -X GET http://localhost:8082/api/bugs/project/1/unassigned \
-H "Authorization: Bearer <MANAGER_TOKEN>"
```

### **✏️ 10. Update Bug**

> `ROLE_TESTER` (if reporter) or `ROLE_PROJECT_MANAGER` / `ROLE_ADMIN`

```bash
curl -X PUT http://localhost:8082/api/bugs/1 \
-H "Content-Type: application/json" \
-H "Authorization: Bearer <TESTER_OR_MANAGER_TOKEN>" \
-d '{
  "title": "Login endpoint returns 500 (stack trace fixed)",
  "description": "Root cause: NPE in UserService. Fixed in hotfix/login-nullptr.",
  "severity": "HIGH",
  "status": "IN_PROGRESS",
  "projectId": 1,
  "assigneeId": 202
}'
```

### **🔄 11. Update Bug Status**

> `ROLE_DEVELOPER` updates assigned bug status;
> `ROLE_TESTER` can mark as verified or reopened.

```bash
# Developer marks as FIXED
curl -X PATCH http://localhost:8082/api/bugs/1/status/FIXED \
-H "Authorization: Bearer <DEVELOPER_TOKEN>"

# Tester verifies fix
curl -X PATCH http://localhost:8082/api/bugs/1/status/VERIFIED \
-H "Authorization: Bearer <TESTER_TOKEN>"
```

### **👷 12. Assign Bug to Developer**

> Only `ROLE_PROJECT_MANAGER` or `ROLE_ADMIN`

```bash
curl -X PATCH http://localhost:8082/api/bugs/1/assign/3 \
-H "Authorization: Bearer <MANAGER_TOKEN>"
```

### **🧹 13. Unassign Bug**

> Only `ROLE_PROJECT_MANAGER` or `ROLE_ADMIN`

```bash
curl -X PATCH http://localhost:8082/api/bugs/1/unassign \
-H "Authorization: Bearer <MANAGER_TOKEN>"
```

### **🧮 14. Count Bugs by Project**

> `ROLE_PROJECT_MANAGER` or `ROLE_ADMIN`

```bash
curl -X GET http://localhost:8082/api/bugs/project/1/count \
-H "Authorization: Bearer <MANAGER_OR_ADMIN_TOKEN>"
```

### **📊 15. Count Bugs by Project and Status**

> `ROLE_PROJECT_MANAGER` or `ROLE_ADMIN`

```bash
curl -X GET http://localhost:8082/api/bugs/project/1/count/status/OPEN \
-H "Authorization: Bearer <MANAGER_OR_ADMIN_TOKEN>"
```

### **🗑️ 16. Delete Bug**

> Only `ROLE_ADMIN`

```bash
curl -X DELETE http://localhost:8082/api/bugs/4 \
-H "Authorization: Bearer <ADMIN_TOKEN>"
```

---

## 🧪 User Management Endpoints”**

> **Base URL:** `http://localhost:8080/api/users`

All requests assume you already have **JWT-based authentication** (if Spring Security is active).
Add your **Authorization header** when needed:

```
Authorization: Bearer <your-access-token>
Content-Type: application/json
```
 
### **1️⃣ Get All Users**

- **GET** `/api/users`

- **✅ Valid Request**

```bash
GET http://localhost:8082/api/users
```

- **Requires:** `ROLE_ADMIN`

- **Expected 200 Response**

```json
[
  {
    "id": 1,
    "username": "admin",
    "email": "admin@example.com",
    "roles": ["admin"],
    "active": true
  },
  {
    "id": 2,
    "username": "john",
    "email": "john@example.com",
    "roles": ["developer"],
    "active": true
  }
]
```

- **❌ Failure Cases**

* **403 Forbidden** → When the token user is not `ADMIN`
* **401 Unauthorized** → No/invalid token
 
### **2️⃣ Get User by ID**

- **GET** `/api/users/{id}`

- **✅ Valid Request**

```
GET http://localhost:8082/api/users/2
```

- **Allowed Roles:** `ADMIN`, `MANAGER`, `DEVELOPER`, `TESTER`

- **Expected 200 Response**

```json
{
  "id": 2,
  "username": "john",
  "email": "john@example.com",
  "roles": ["developer"],
  "active": true
}
```

- **❌ Failure Cases**

* **404 Not Found** → User ID doesn’t exist
* **403 Forbidden** → Accessing another user’s data without admin privileges
* **401 Unauthorized** → Missing or expired token
 
### **3️⃣ Get Users by Role**

- **GET** `/api/users/role/{role}`

- **✅ Valid Request**

```
GET http://localhost:8082/api/users/role/ROLE_DEVELOPER
```

- **Allowed Roles:** `ADMIN`, `MANAGER`

- **Expected 200 Response**

```json
[
  {
    "id": 2,
    "username": "john",
    "email": "john@example.com",
    "roles": ["developer"],
    "active": true
  }
]
```

- **❌ Failure Cases**

* **400 Bad Request** → If you pass an invalid enum, e.g. `/role/DEV`
* **403 Forbidden** → If requester is not `ADMIN` or `MANAGER`
 
### **4️⃣ Count Users by Role**

- **GET** `/api/users/count/role/{role}`

- **✅ Valid Request**

```
GET http://localhost:8082/api/users/count/role/ROLE_DEVELOPER
```

- **Expected 200 Response**

```json
3
```

- **❌ Failure Cases**

* **400 Bad Request** → Invalid enum constant
* **403 Forbidden** → Insufficient privileges
 
### **5️⃣ Update User Info**

- **PUT** `/api/users/{id}`

- **✅ Valid Request**

```
PUT http://localhost:8082/api/users/2
```

- **Body:**

```json
{
  "username": "john_updated",
  "email": "john.new@example.com",
  "roles": ["ROLE_DEVELOPER"]
}
```

- **Allowed Roles:** `ADMIN`, `MANAGER`, or the user themselves.

- **Expected 200 Response**

```json
{
  "id": 2,
  "username": "john_updated",
  "email": "john.new@example.com",
  "roles": ["developer"],
  "active": true
}
```

- **❌ Failure Cases**

* **400 Bad Request** → Invalid body (missing required fields)
* **404 Not Found** → ID doesn’t exist
* **403 Forbidden** → User tries to edit another user without being admin
 
### **6️⃣ Deactivate User**

- **PATCH** `/api/users/{id}/deactivate`

- **✅ Valid Request**

```
PATCH http://localhost:8082/api/users/2/deactivate
```

- **Requires:** `ROLE_ADMIN`

- **Expected 200 Response**

```json
{
  "id": 2,
  "username": "john_updated",
  "email": "john.new@example.com",
  "roles": ["developer"],
  "active": false
}
```

- **❌ Failure Cases**

* **403 Forbidden** → Not admin
* **404 Not Found** → Invalid ID
 
### **7️⃣ Delete User**

- **DELETE** `/api/users/{id}`

- **✅ Valid Request**

```
DELETE http://localhost:8082/api/users/2
```

- **Requires:** `ROLE_ADMIN`

- **Expected 204 No Content**

- **❌ Failure Cases**

* **404 Not Found** → User ID doesn’t exist
* **403 Forbidden** → Not admin
* **409 Conflict** → If user cannot be deleted due to relationships (depends on DB constraints)

---
