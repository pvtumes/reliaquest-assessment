# ReliaQuest's Entry-Level Java Challenge

Please keep the following in mind while working on this challenge:
* Code implementations will not be graded for **correctness** but rather on practicality
* Articulate clear and concise design methodologies, if necessary
* Use clean coding etiquette
  * E.g. avoid liberal use of new-lines, odd variable and method names, random indentation, etc...
* Test cases are not required

## Problem Statement

Your employer has recently purchased a license to top-tier SaaS platform, Employees-R-US, to off-load all employee management responsibilities.
Unfortunately, your company's product has an existing employee management solution that is tightly coupled to other services and therefore 
cannot be replaced whole-cloth. Product and Development leads in your department have decided it would be best to interface
the existing employee management solution with the commercial offering from Employees-R-US for the time being until all employees can be
migrated to the new SaaS platform.

Your ask is to expose employee information as a protected, secure REST API for consumption by Employees-R-US web hooks.
The initial REST API will consist of 3 endpoints, listed in the following section. If for any reason the implementation 
of an endpoint is problematic, the team lead will accept **pseudo-code** and a pertinent description (e.g. java-doc) of intent.

Good luck!

## Endpoints to implement (API module)

_See `com.challenge.api.controller.EmployeeController` for details._

getAllEmployees()

    output - list of employees
    description - this should return all employees, unfiltered

getEmployeeByUuid(...)

    path variable - employee UUID
    output - employee
    description - this should return a single employee based on the provided employee UUID

createEmployee(...)

    request body - attributes necessary to create an employee
    output - employee
    description - this should return a single employee, if created, otherwise error

## Code Formatting

This project utilizes Gradle plugin [Diffplug Spotless](https://github.com/diffplug/spotless/tree/main/plugin-gradle) to enforce format
and style guidelines with every build.

To format code according to style guidelines, you can run **spotlessApply** task.
`./gradlew spotlessApply`

The spotless plugin will also execute check-and-validation tasks as part of the gradle **build** task.
`./gradlew build`

## Implementation & API Documentation

### Running the Application
```bash
./gradlew bootRun
```
The server starts on port `8080` (Base URL: `http://localhost:8080`).

---

### Endpoints

#### 1. Get All Employees
- **Endpoint**: `GET /api/v1/employee`
- **Description**: Returns all employees in the in-memory data store.
- **Example Request**:
```bash
curl -X GET http://localhost:8080/api/v1/employee
```
- **Example Response (200 OK)**:
```json
[
  {
    "uuid": "11111111-1111-1111-1111-111111111111",
    "firstName": "Umesh",
    "lastName": "Prasad",
    "fullName": "Umesh Prasad",
    "salary": 95000,
    "age": 22,
    "jobTitle": "Java Backend Developer",
    "email": "pvtumes@gmail.com",
    "contractHireDate": "2026-07-27T03:00:00Z",
    "contractTerminationDate": null
  },
  {
    "uuid": "22222222-2222-2222-2222-222222222222",
    "firstName": "Mukesh",
    "lastName": "Prasad",
    "fullName": "Mukesh Prasad",
    "salary": 105000,
    "age": 25,
    "jobTitle": "Full Stack Developer",
    "email": "pvtmukes@gmail.com",
    "contractHireDate": "2026-07-27T03:00:00Z",
    "contractTerminationDate": null
  }
]
```

#### 2. Get Employee By UUID
- **Endpoint**: `GET /api/v1/employee/{uuid}`
- **Description**: Retrieves a single employee by their UUID. Returns `404 Not Found` if the UUID does not exist.
- **Example Request**:
```bash
curl -X GET http://localhost:8080/api/v1/employee/11111111-1111-1111-1111-111111111111
```
- **Example Response (200 OK)**:
```json
{
  "uuid": "11111111-1111-1111-1111-111111111111",
  "firstName": "Umesh",
  "lastName": "Prasad",
  "fullName": "Umesh Prasad",
  "salary": 95000,
  "age": 22,
  "jobTitle": "Java Backend Developer",
  "email": "pvtumes@gmail.com",
  "contractHireDate": "2026-07-27T03:00:00Z",
  "contractTerminationDate": null
}
```

#### 3. Create Employee
- **Endpoint**: `POST /api/v1/employee`
- **Description**: Creates a new employee. `firstName`, `lastName`, and `email` are mandatory fields. Returns `400 Bad Request` if any required field is missing or empty.
- **Example Request**:
```bash
curl -X POST http://localhost:8080/api/v1/employee \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Rajesh",
    "lastName": "Prasad",
    "salary": 90000,
    "age": 24,
    "jobTitle": "Java Developer",
    "email": "pvtrajesh@gmail.com"
  }'
```
- **Example Response (200 OK)**:
```json
{
  "uuid": "8f2a1b90-7c3e-4b2a-9e1d-5f8a4c3b2a1e",
  "firstName": "Rajesh",
  "lastName": "Prasad",
  "fullName": "Rajesh Prasad",
  "salary": 90000,
  "age": 24,
  "jobTitle": "Java Developer",
  "email": "pvtrajesh@gmail.com",
  "contractHireDate": "2026-07-27T03:45:00Z",
  "contractTerminationDate": null
}
```

#### 4. Validation Error Response Example
- **Scenario**: Missing required field (`lastName` is empty/missing).
- **Response (400 Bad Request)**:
```json
{
  "timestamp": "2026-07-27T03:45:00.000+00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "First name, last name, and email are required",
  "path": "/api/v1/employee"
}
```
