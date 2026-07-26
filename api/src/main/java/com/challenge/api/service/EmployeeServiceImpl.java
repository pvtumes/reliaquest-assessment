package com.challenge.api.service;

import com.challenge.api.dto.CreateEmployeeRequest;
import com.challenge.api.model.Employee;
import com.challenge.api.model.EmployeeImpl;
import com.challenge.api.repository.EmployeeRepository;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeByUuid(UUID uuid) {
        if (uuid == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee UUID must not be null");
        }
        return employeeRepository
                .findByUuid(uuid)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found with UUID: " + uuid));
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

        return employeeRepository.save(employee);
    }

    private boolean isInvalid(String value) {
        return value == null || value.trim().isEmpty();
    }
}
