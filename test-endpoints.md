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




---

## Bug Endpoints




---

