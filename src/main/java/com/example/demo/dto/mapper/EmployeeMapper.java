package com.example.demo.dto.mapper;

import com.example.demo.dto.EmployeeRequest;
import com.example.demo.dto.EmployeeResponse;
import com.example.demo.entity.Employee;
import org.springframework.beans.BeanUtils;

import java.util.List;

public class EmployeeMapper {
    public static EmployeeResponse toResponse(Employee employee) {
        EmployeeResponse employeeResponse = new EmployeeResponse();
        BeanUtils.copyProperties(employee, employeeResponse);
        return employeeResponse;
    }

    public static List<EmployeeResponse> toResponse(List<Employee> employees) {
        return employees.stream().map(EmployeeMapper::toResponse).toList();
    }

    public static EmployeeRequest toRequest(Employee employee) {
        EmployeeRequest employeeRequest = new EmployeeRequest();
        BeanUtils.copyProperties(employee, employeeRequest);
        return employeeRequest;
    }

    public static List<EmployeeRequest> toRequest(List<Employee> employees) {
        return employees.stream().map(EmployeeMapper::toRequest).toList();
    }

}
