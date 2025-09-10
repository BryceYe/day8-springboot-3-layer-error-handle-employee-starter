package com.example.demo.service.impl;

import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    public List<Employee> getEmployees(String gender, Integer page, Integer size) {
        return employeeRepository.getEmployees(gender, page, size);
    }

    public Employee getEmployeeById(int id) {
        Employee employee = employeeRepository.getEmployeeById(id);
        if(employee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found with id: " + id);
        }
        return employee;
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepository.createEmployee(employee);
    }

    public Employee updateEmployee(int id, Employee updatedEmployee) {
        Employee found = getEmployeeById(id);
        return employeeRepository.updateEmployee(found, updatedEmployee);
    }

    public void deleteEmployee(int id) {
        Employee found = getEmployeeById(id);
        employeeRepository.deleteEmployee(found);
    }

    public void empty() {
        employeeRepository.empty();
    }
}
