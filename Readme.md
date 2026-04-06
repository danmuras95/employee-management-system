# Employee Management System

A Spring Boot HR management system providing REST endpoints for employee, department, and leave request management.  
It follows a layered architecture and includes validation, business rules, DTO mapping, and unit testing.

---

## Features

- Manage employees, departments, and leave requests
- Full CRUD operations for all entities
- Pagination support (`page`, `size`, `sort`)
- Filtering (e.g., leave requests by type and status)
- Input validation to ensure data integrity

---

## Technical Highlights

- Global exception handling with meaningful HTTP responses
- Unit-tested service layer using Mockito
- Structured logging for monitoring and debugging

---

## Architecture

This project follows a layered architecture:

- **Controller** → handles HTTP requests
- **Service** → contains business logic
- **Repository** → handles database access
- **DTO + Mapper** → ensures clean API contracts

---

## Deployment & Environment

- Containerized using Docker
- Multi-container setup with Docker Compose (Spring Boot + PostgreSQL)
- Isolated environment for consistent setup across machines
- Persistent storage using Docker volumes

---

## Tech Stack

- Java
- Spring Boot
- Spring Data JPA (Hibernate)
- PostgreSQL
- Mockito (unit testing)
- Maven

---

## Project Structure

    controller/   → REST endpoints  
    service/      → business logic  
    repository/   → data access  
    entity/       → JPA entities  
    dto/          → API models  
    mapper/       → entity ↔ DTO conversion  
    exception/    → custom exceptions & handlers  
    enums/        → domain enums  

---

## API Endpoints (Examples)

### Employee

    GET /employees?page=0&size=10
    GET /employees/{employeeId}
    POST /employees
    PUT /employees/{employeeId}
    DELETE /employees/{employeeId}

### Department

    GET /departments
    GET /departments/{departmentId}
    POST /departments
    PUT /departments/{departmentId}
    DELETE /departments/{departmentId}

### Leave Requests

    GET /leaveRequests?leaveType=VACATION
    GET /leaveRequests?leaveStatus=APPROVED
    POST /leaveRequests
    PUT /leaveRequests/{leaveRequestId}
    PUT /leaveRequests/{leaveRequestId}/cancel

---

## Testing

- Service layer unit tests implemented
- Mockito used for mocking dependencies
- Covers both success and error scenarios

---

## Setup & Run

### Clone the Repository

    git clone https://github.com/danmuras95/employee-management-system.git
    cd employee-management-system

---

## Run with Docker (Recommended)

    docker-compose up --build

**Details:**
- Uses `application-docker.yml`
- Starts both the PostgreSQL container and the application
- Database is automatically populated using `data.sql`
- App runs at: http://localhost:8080

---

## Local Development (PostgreSQL on Your Machine)

### Build the project

    mvn clean package

### Run the application

    mvn spring-boot:run -Dspring-boot.run.profiles=local

**Requirements:**
- PostgreSQL must be running locally
- Configure credentials in `application-local.yml`

**Access the app at:**

    http://localhost:8080

---

## Sample API Requests

### Get all employees

```http
GET /employees
Accept: application/json
```

---

### Create a new department

```http
POST /departments
Content-Type: application/json
```

```json
{
  "name": "IT",
  "location": "Floor 1"
}
```

---

### Create a new employee

```http
POST /employees
Content-Type: application/json
```

```json
{
  "firstName": "Anne",
  "lastName": "Shirley",
  "email": "anne.shirley@example.com",
  "departmentId": 1,
  "managerId": null,
  "role": "MANAGER",
  "salary": 50000
}
```

---

### Submit a leave request

```http
POST /leaveRequests
Content-Type: application/json
```

```json
{
  "employeeId": 1,
  "startDate": "2026-06-01",
  "endDate": "2026-06-14",
  "leaveType": "VACATION"
}
```

---

## Future Improvements

- Add authentication & authorization (Spring Security + JWT)
- Introduce automated integration tests for end-to-end functionality
- Add API documentation (Swagger / OpenAPI)
- Implement caching for performance optimization
- Add file upload support for employee resumes (PDF/DOC) with validation and secure storage