package com.example.demo.service;

import com.example.demo.dto.EmployeeResponse;
import com.example.demo.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<EmployeeResponse> getEmployees(String gender, Integer page, Integer size);

    EmployeeResponse getEmployeeById(int id);

    EmployeeResponse createEmployee(Employee employee);

    EmployeeResponse updateEmployee(int id, Employee updatedEmployee);

    void deleteEmployee(int id);
}
