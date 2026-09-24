# Todo App API

A RESTful API built with Spring Boot that allows users to manage their personal task lists. Each user can create, update, and delete their own tasks, with secure access protected by JWT authentication.

---

## Tech Stack

- **Java 21**
- **Spring Boot 4.1.0**
- **Maven**
- **PostgreSQL**
- **Spring Security + JWT + BCrypt**
- **Spring Data JPA + Hibernate**
- **Spring Validation**
- **Swagger / OpenAPI (Springdoc)**
- **JUnit 5 + Mockito**

---

## Features

- User registration and management (create, update, delete)
- Task management per user (create, update, delete, list)
- Assign multiple tasks to a specific user in a single request
- JWT authentication — login returns a token required for all protected endpoints
- Input validation — empty fields are rejected with descriptive error messages
- Global error handling with custom exceptions
- Interactive API documentation via Swagger UI
- Unit tests covering all Service layer methods (17 tests total)

---

## Prerequisites

- Java 21
- PostgreSQL
- IntelliJ IDEA (Community Edition is free)

---

## Getting Started

1. Clone the repository
```bash
git clone https://github.com/your-username/your-repo-name.git
```

2. Create a PostgreSQL database named `tododb`

3. Create an `application-local.properties` file in `src/main/resources/` with your credentials (see Environment Variables section)

4. Open the project in IntelliJ IDEA and let Maven download the dependencies automatically

5. Run `DemoApplication.java`

6. Access the API at `http://localhost:8080`

7. View the interactive documentation at `http://localhost:8080/swagger-ui/index.html`

---

## Environment Variables

Create an `application-local.properties` file in `src/main/resources/` with the following:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/tododb
spring.datasource.username=your_username
spring.datasource.password=your_password
jwt.secret=your_secret_key_at_least_32_characters_long
```

> **Note:** This file is listed in `.gitignore` and should never be committed to version control.

---

## API Endpoints

### Authentication
| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | /auth/login | Authenticate user and receive JWT token | No |

### Tasks
| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | /tareas | Get all tasks | Yes |
| POST | /tareas | Create a new task | Yes |
| POST | /tareas/lista | Create multiple tasks at once | Yes |
| PUT | /tareas/{id} | Update a task by ID | Yes |
| DELETE | /tareas/{id} | Delete a task by ID | Yes |

### Users
| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | /usuarios | Get all users | Yes |
| POST | /usuarios | Create a new user | No |
| PUT | /usuarios/{id} | Update a user by ID | Yes |
| DELETE | /usuarios/{id} | Delete a user by ID | Yes |
| GET | /usuarios/{id}/tareas | Get all tasks for a user | Yes |
| POST | /usuarios/{id}/tareas | Add a task to a user | Yes |

### Authentication Usage

After logging in, include the JWT token in every request header:
```
Authorization: Bearer your_jwt_token_here
```

---

## Architecture Decisions

### Layered Architecture (MVC + Service + Repository)
The project is structured in clear layers with separated responsibilities:
- **Controller** — handles HTTP requests and delegates to the Service
- **Service** — contains all business logic
- **Repository** — manages database access
- **Model** — defines the data structure

This separation makes the code maintainable, testable and scalable. For example, changing from PostgreSQL to another database would only affect the Repository layer without touching business logic.

### JWT over Sessions
JWT is stateless — the server doesn't store session data. Each request carries all authentication information in the token, making the API more scalable and suitable for distributed systems.

### BCrypt for Password Encryption
Passwords are never stored in plain text. BCrypt automatically handles salting and is computationally expensive by design, making it resistant to brute force attacks.

### Custom Exceptions with @ControllerAdvice
Instead of returning generic error responses, custom exceptions (`TareaNotFoundException`, `UsuarioNotFoundException`) combined with `@ControllerAdvice` provide descriptive, consistent error messages across all endpoints — 404 for missing resources, 400 for invalid input.

### Optional for Null Safety
Using `Optional` when searching by ID prevents `NullPointerException` and forces explicit handling of the case where a resource doesn't exist, resulting in proper 404 responses instead of 500 errors.

### Spring Validation with @NotBlank
Input validation is handled at the model level using Bean Validation annotations, ensuring invalid data never reaches the business logic or database layer.

### @OneToMany / @ManyToOne Relationship
The relationship between `Usuario` and `Tarea` is managed through JPA annotations, with a foreign key (`usuario_id`) in the `tarea` table. `@JsonIgnore` is used on the tasks list in `Usuario` to prevent infinite recursion during JSON serialization.

### Unit Testing with Mockito
The Service layer is tested in isolation using Mockito to mock the Repository dependencies. This means tests run without a real database connection, making them fast and reliable. `PasswordEncoder` is also mocked in `UsuarioService` tests to isolate business logic from Spring Security dependencies.

---

## API Documentation

The full interactive API documentation is available via Swagger UI once the application is running:

```
http://localhost:8080/swagger-ui/index.html
```

From Swagger UI you can explore all endpoints, view request/response models, and test the API directly from the browser using JWT authentication.

---

## Running Tests

Run all unit tests with:

```bash
mvn test
```

The project includes 17 unit tests covering all methods in `TareaService` and `UsuarioService`, including both happy path and error scenarios.
