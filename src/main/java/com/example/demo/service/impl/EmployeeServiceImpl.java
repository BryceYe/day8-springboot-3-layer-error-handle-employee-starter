package com.example.demo.service.impl;

import com.example.demo.dto.EmployeeRequest;
import com.example.demo.dto.EmployeeResponse;
import com.example.demo.dto.mapper.EmployeeMapper;
import com.example.demo.entity.Employee;
import com.example.demo.exception.InvalidAgeEmployeeException;
import com.example.demo.exception.InvalidSalaryEmployeeException;
import com.example.demo.exception.UpdateEmployeeException;
import com.example.demo.repository.IEmployeeRepository;
import com.example.demo.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final IEmployeeRepository employeeRepository;

    public List<EmployeeResponse> getEmployees(String gender, Integer page, Integer size) {
        if(gender == null){
            if(page == null || size == null){
                return EmployeeMapper.toResponse(employeeRepository.findAll());
            } else {
                Pageable pageable = PageRequest.of(page - 1, size);
                return EmployeeMapper.toResponse(employeeRepository.findAll(pageable).toList());
            }
        } else {
            if(page == null || size == null){
                return EmployeeMapper.toResponse(employeeRepository.findEmployeesByGender(gender));
            } else {
                Pageable pageable = PageRequest.of(page - 1, size);
                return EmployeeMapper.toResponse(employeeRepository.findEmployeesByGender(gender, pageable));
            }
        }
    }

    public EmployeeResponse getEmployeeById(int id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found with id: " + id);
        }
        return EmployeeMapper.toResponse(employee.get());
    }

    public EmployeeResponse createEmployee(EmployeeRequest request) {
        if(request.getAge() == null) {
            throw new InvalidAgeEmployeeException("employee age is null");
        }
        if(request.getAge() < 18 || request.getAge() > 65) {
            throw new InvalidAgeEmployeeException("employee age less than 18 or greater than 65");
        }
        if(request.getAge() >= 30 && request.getSalary() < 20000.0){
            throw new InvalidSalaryEmployeeException("employee salary less than 20000");
        }
        Employee employee = new Employee();
        BeanUtils.copyProperties(request, employee);
        return EmployeeMapper.toResponse(employeeRepository.save(employee));
    }

    public EmployeeResponse updateEmployee(int id, EmployeeRequest request) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isEmpty()) {
            throw new UpdateEmployeeException("Employee is not found");
        }
        if(!employee.get().isActive()){
            throw new UpdateEmployeeException("Employee's active is false");
        }
        Employee updatedEmployee = new Employee();
        BeanUtils.copyProperties(request, updatedEmployee);
        updatedEmployee.setId(id);
        return EmployeeMapper.toResponse(employeeRepository.save(updatedEmployee));
    }

    public void deleteEmployee(int id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found with id: " + id);
        }
        Employee entity = employee.get();
        entity.setActive(false);
        employeeRepository.save(entity);
    }
}
