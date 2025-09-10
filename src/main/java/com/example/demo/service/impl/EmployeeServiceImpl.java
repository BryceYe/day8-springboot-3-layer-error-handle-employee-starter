package com.example.demo.service.impl;

import com.example.demo.entity.Employee;
import com.example.demo.exception.InvalidAgeEmployeeException;
import com.example.demo.exception.InvalidSalaryEmployeeException;
import com.example.demo.exception.UpdateEmployeeException;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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
        if(employee.getAge() == null) {
            throw new InvalidAgeEmployeeException("employee age is null");
        }
        if(employee.getAge() < 18 || employee.getAge() > 65) {
            throw new InvalidAgeEmployeeException("employee age less than 18 or greater than 65");
        }
        if(employee.getAge() >= 30 && employee.getSalary() < 20000.0){
            throw new InvalidSalaryEmployeeException("employee salary less than 20000");
        }
        return employeeRepository.createEmployee(employee);
    }

    public Employee updateEmployee(int id, Employee updatedEmployee) {
        Employee found = employeeRepository.getEmployeeById(id);
        if(found == null) {
            throw new UpdateEmployeeException("Employee is not found");
        }
        if(!found.isActive()){
            throw new UpdateEmployeeException("Employee's active is false");
        }
        return employeeRepository.updateEmployee(id, updatedEmployee);
    }

    public void deleteEmployee(int id) {
        Employee found = getEmployeeById(id);
        found.setActive(false);
//        employeeRepository.deleteEmployee(found);
    }

    public void empty() {
        employeeRepository.empty();
    }
}
