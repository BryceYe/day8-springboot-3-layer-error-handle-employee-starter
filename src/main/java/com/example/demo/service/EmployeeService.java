package com.example.demo.service;

import com.example.demo.dto.EmployeeRequest;
import com.example.demo.dto.EmployeeResponse;

import java.util.List;

public interface EmployeeService {
    List<EmployeeResponse> getEmployees(String gender, Integer page, Integer size);

    EmployeeResponse getEmployeeById(int id);

    EmployeeResponse createEmployee(EmployeeRequest request);

    EmployeeResponse updateEmployee(int id, EmployeeRequest request);

    void deleteEmployee(int id);
}
