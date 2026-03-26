# Employee Management System

A Spring Boot HR management system providing REST endpoints for employee, department, and leave request management, featuring validation, layered architecture, business rules, and unit-tested services.

## Features
- Manage employees, departments, and leave requests
- Full CRUD operations for all entities
- Filtering endpoints (e.g., leave requests by type or status)
- Input validation to ensure data integrity
- Global exception handling with meaningful HTTP responses
- Unit tests for service layer using Mockito

## Tech Stack
- Java
- Spring Boot
- Spring Data JPA (Hibernate)
- PostgreSQL
- Mockito (unit testing)
- Maven

## Project Structure
- controller/
- dto/
- entity/
- enums/
- exception/
- mapper/
- repository/
- service/

## API Endpoints (examples)

### Employee
- `GET /employees`
- `GET /employees/{employeeId}`
- `POST /employees`
- `PUT /employees/{employeeId}`
- `DELETE /employees/{employeeId}`

### Department
- `GET /departments`
- `GET /departments/{departmentId}`
- `POST /departments`
- `PUT /departments/{departmentId}`
- `DELETE /departments/{departmentId}`

### Leave Requests
- `GET /leaveRequests?leaveType=VACATION`
- `GET /leaveRequests?leaveStatus=APPROVED`
- `POST /leaveRequests`
- `PUT /leaveRequests/{leaveRequestId}`
- `PUT /leaveRequests/{leaveRequestId}/cancel`

## Testing
- Service layer unit tests implemented
- Mockito used for mocking dependencies
- Covers both success and error scenarios

## Setup & Run
1. Clone the repository:
   git clone https://github.com/danmuras95/employee-management-system.git
2. Configure the database in `application.yml`
3. Run the application:
   mvn spring-boot:run

## Sample API Requests

### Get all employees
GET /employees  
Accept: application/json

### Create a new department
POST /departments  
Content-Type: application/json

{
"name": "IT",
"location": "Floor 1"
}

### Create a new Employee
POST /employees  
Content-Type: application/json

{
"firstName": "Anne",
"lastName": "Shirley",
"email": "anne.shirley@example.com",
"departmentId": 1,
"managerId": null,
"role": "MANAGER",
"salary": 50000
}

### Submit a leave request
POST /leaveRequests  
Content-Type: application/json

{
"employeeId": 1,
"startDate": "2026-06-01",
"endDate": "2026-06-14",
"leaveType": "VACATION"
}