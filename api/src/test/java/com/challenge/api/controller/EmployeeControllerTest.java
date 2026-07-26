package com.challenge.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.challenge.api.dto.CreateEmployeeRequest;
import com.challenge.api.model.Employee;
import com.challenge.api.repository.EmployeeRepositoryImpl;
import com.challenge.api.service.EmployeeServiceImpl;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

public class EmployeeControllerTest {

    private final EmployeeController controller =
            new EmployeeController(new EmployeeServiceImpl(new EmployeeRepositoryImpl()));

    @Test
    public void testGetAllEmployees() {
        List<Employee> employees = controller.getAllEmployees();
        assertEquals(3, employees.size());
    }

    @Test
    public void testGetEmployeeByUuid() {
        UUID uuid = UUID.fromString("11111111-1111-1111-1111-111111111111");
        Employee employee = controller.getEmployeeByUuid(uuid);

        assertNotNull(employee);
        assertEquals("Umesh", employee.getFirstName());
        assertEquals("Prasad", employee.getLastName());
        assertEquals("pvtumes@gmail.com", employee.getEmail());
    }

    @Test
    public void testGetEmployeeByUuidNotFound() {
        assertThrows(ResponseStatusException.class, () -> {
            controller.getEmployeeByUuid(UUID.randomUUID());
        });
    }

    @Test
    public void testCreateEmployee() {
        CreateEmployeeRequest request =
                new CreateEmployeeRequest("Rajesh", "Prasad", 90000, 24, "Java Developer", "pvtrajesh@gmail.com");

        Employee created = controller.createEmployee(request);

        assertNotNull(created.getUuid());
        assertEquals("Rajesh", created.getFirstName());
        assertEquals("Prasad", created.getLastName());
        assertEquals("Rajesh Prasad", created.getFullName());
        assertEquals("pvtrajesh@gmail.com", created.getEmail());
    }

    @Test
    public void testCreateEmployeeEmptyField() {
        CreateEmployeeRequest request =
                new CreateEmployeeRequest("Rajesh", "", 90000, 24, "Java Developer", "pvtrajesh@gmail.com");

        assertThrows(ResponseStatusException.class, () -> {
            controller.createEmployee(request);
        });
    }
}
