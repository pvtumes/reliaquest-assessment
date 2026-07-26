package com.challenge.api.service;

import com.challenge.api.dto.CreateEmployeeRequest;
import com.challenge.api.model.Employee;
import com.challenge.api.model.EmployeeImpl;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final Map<UUID, Employee> employeeStore = new ConcurrentHashMap<>();

    public EmployeeServiceImpl() {
        initMockData();
    }

    private void initMockData() {
        Employee emp1 = new EmployeeImpl(
                UUID.fromString("11111111-1111-1111-1111-111111111111"),
                "Umesh",
                "Prasad",
                95000,
                22,
                "Java Backend Developer",
                "pvtumes@gmail.com",
                Instant.now());

        Employee emp2 = new EmployeeImpl(
                UUID.fromString("22222222-2222-2222-2222-222222222222"),
                "Mukesh",
                "Prasad",
                105000,
                25,
                "Full Stack Developer",
                "pvtmukes@gmail.com",
                Instant.now());

        Employee emp3 = new EmployeeImpl(
                UUID.fromString("33333333-3333-3333-3333-333333333333"),
                "Ramesh",
                "Prasad",
                115000,
                28,
                "Senior DevOps Engineer",
                "pvtrames@gmail.com",
                Instant.now());

        employeeStore.put(emp1.getUuid(), emp1);
        employeeStore.put(emp2.getUuid(), emp2);
        employeeStore.put(emp3.getUuid(), emp3);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employeeStore.values());
    }

    @Override
    public Employee getEmployeeByUuid(UUID uuid) {
        if (uuid == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee UUID must not be null");
        }
        Employee employee = employeeStore.get(uuid);
        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found with UUID: " + uuid);
        }
        return employee;
    }

    @Override
    public Employee createEmployee(CreateEmployeeRequest request) {
        if (request == null
                || isInvalid(request.getFirstName())
                || isInvalid(request.getLastName())
                || isInvalid(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "First name, last name, and email are required");
        }

        UUID uuid = UUID.randomUUID();
        Employee employee = new EmployeeImpl(
                uuid,
                request.getFirstName(),
                request.getLastName(),
                request.getSalary(),
                request.getAge(),
                request.getJobTitle(),
                request.getEmail(),
                Instant.now());

        employeeStore.put(uuid, employee);
        return employee;
    }

    private boolean isInvalid(String value) {
        return value == null || value.trim().isEmpty();
    }
}
