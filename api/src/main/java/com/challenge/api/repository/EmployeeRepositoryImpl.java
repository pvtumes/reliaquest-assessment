package com.challenge.api.repository;

import com.challenge.api.model.Employee;
import com.challenge.api.model.EmployeeImpl;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private final Map<UUID, Employee> employeeStore = new ConcurrentHashMap<>();

    public EmployeeRepositoryImpl() {
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
    public List<Employee> findAll() {
        return new ArrayList<>(employeeStore.values());
    }

    @Override
    public Optional<Employee> findByUuid(UUID uuid) {
        return Optional.ofNullable(employeeStore.get(uuid));
    }

    @Override
    public Employee save(Employee employee) {
        employeeStore.put(employee.getUuid(), employee);
        return employee;
    }
}
