# Task Manager API

REST API for task management, developed during the Itaú Java and
Artificial Intelligence Bootcamp offered by DIO.

## Current Features

- Create a task
- List all tasks
- Find a task by ID
- Find a task by Status
- Update a task
- Delete a task
- Validate request data
- Handle task-not-found errors
- Generate API documentation with Spring REST Docs and Asciidoctor

## Technologies

- Java 26
- Spring Boot
- Spring Web
- Bean Validation
- JUnit
- Mockito
- Spring REST Docs
- Asciidoctor
- Gradle

## Endpoints

| Method   | Endpoint             | Description            |
|----------|----------------------|------------------------|
| `POST`   | `/tasks`             | Creates a task         |
| `GET`    | `/tasks`             | Lists all tasks        |
| `GET`    | `/tasks/{id}`        | Finds a task by ID     |
| `GET`    | `/tasks/status/{id}` | Finds a task by Status |
| `PATCH`  | `/tasks/{id}`        | Updates a task         |
| `DELETE` | `/tasks/{id}`        | Deletes a task         |

## Create Task Example

```json
{
  "title": "Study Spring Boot",
  "description": "Practice building REST APIs"
}
```
## Author

**Neylon Eduardo**

Computer Science student focused on Java, Spring Boot and backend development.