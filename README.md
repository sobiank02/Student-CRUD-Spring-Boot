# Student CRUD Application

## Overview
The Student CRUD Application is a Spring Boot project that provides REST APIs to manage student records. It supports creating, viewing, updating, and deleting student information.

## Technologies Used
- Java 21
- Spring Boot
- Spring Data JPA
- H2 In-Memory Database
- Maven
- Jakarta Bean Validation
- REST API

## Features
- Create a new student record.
- Retrieve all student records.
- Retrieve a student by ID.
- Update existing student details.
- Delete a student record.
- Validate student input, including name, email, age, and course.
- Return appropriate HTTP status codes.
- Handle missing students with HTTP 404 responses.
- Test API functionality using automated tests.

## API Endpoints

| Method | Endpoint | Description | Expected Status |
|---|---|---|---|
| POST | `/students` | Create a student | 201 Created |
| GET | `/students` | Retrieve all students | 200 OK |
| GET | `/students/{id}` | Retrieve a student by ID | 200 OK |
| PUT | `/students/{id}` | Update a student | 200 OK |
| DELETE | `/students/{id}` | Delete a student | 204 No Content |

Invalid student data returns `400 Bad Request`. A student that cannot be found returns `404 Not Found`.

## How to Run the Project

1. Install a compatible JDK and ensure Java is configured.
2. Open a terminal in the project directory.
3. Run the following command:

```powershell
.\mvnw.cmd spring-boot:run
```

The application runs at `http://localhost:8080`.

## Running Tests

Execute the following command to run the automated tests:

```powershell
.\mvnw.cmd clean test
```

The test suite covers application startup and the main CRUD operations, including validation and missing-student handling.

## Database
The application uses an H2 in-memory database. Data is not retained after the application restarts.

The H2 console is available at `/h2-console` during development. It is disabled when the `prod` profile is active.

## Project Purpose
This project demonstrates REST API development, database operations, input validation, exception handling, and automated testing using Spring Boot.