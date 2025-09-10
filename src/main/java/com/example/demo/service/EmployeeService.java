package com.example.demo.service;

import com.example.demo.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getEmployees(String gender, Integer page, Integer size);

    Employee getEmployeeById(int id);

    Employee createEmployee(Employee employee);

    void empty();
}
