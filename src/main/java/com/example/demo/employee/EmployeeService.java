package com.example.demo.employee;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    public void addNewEmployee(Employee employee) {
        Optional<Employee> employeeByEmail = employeeRepository.findEmployeeByEmail(employee.getEmail());

        if (employeeByEmail.isPresent()) {
            throw new IllegalStateException("email taken");
        }

        employeeRepository.save(employee);
    }

    public void deleteEmployee(Integer employeeId) {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);

        if (employeeOptional.isEmpty()) {
            throw new IllegalStateException(
                    "Employee with id " + employeeId + " does not exist"
            );
        }

        employeeRepository.deleteById(employeeId);
    }

    @Transactional
    public void updateEmployee(Integer employeeId, String firstName, String lastName, String email) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalStateException(
                        "Employee with id" + employeeId + "does not exist"
                ));

        if (firstName != null &&
                !firstName.isEmpty() &&
                !Objects.equals(employee.getFirst_name(), firstName)) {
            employee.setFirst_name(firstName);
        }

        if (lastName != null &&
                !lastName.isEmpty() &&
                !Objects.equals(employee.getLast_name(), lastName)) {
            employee.setLast_name(lastName);
        }

        if (email != null &&
                !email.isEmpty() &&
                !Objects.equals(employee.getEmail(), email)) {

            Optional<Employee> employeeByEmail = employeeRepository.findEmployeeByEmail(employee.getEmail());

            if (employeeByEmail.isPresent()) {
                throw new IllegalStateException("email taken");
            }
            employee.setEmail(email);
        }
    }
}
