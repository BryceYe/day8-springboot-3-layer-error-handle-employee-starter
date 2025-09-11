package com.example.demo;

import com.example.demo.dto.EmployeeRequest;
import com.example.demo.dto.EmployeeResponse;
import com.example.demo.entity.Employee;
import com.example.demo.exception.InvalidAgeEmployeeException;
import com.example.demo.exception.InvalidSalaryEmployeeException;
import com.example.demo.repository.IEmployeeRepository;
import com.example.demo.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {
    @InjectMocks
    private EmployeeServiceImpl employeeServiceImpl;

    @Mock
    private IEmployeeRepository employeeRepository;

    @Test
    void should_exception_when_create_an_employee(){
        Employee employee = new Employee(null, "Tom", 20, "MALE", 20000.0);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        EmployeeRequest request = new EmployeeRequest(null, "Tom", 20, "MALE", 20000.0);

        EmployeeResponse employeeResult = employeeServiceImpl.createEmployee(request);

        verify(employeeRepository, atLeastOnce()).save(any(Employee.class));
        assertEquals(employeeResult.getAge(), employee.getAge());
    }

    @Test
    void should_throw_exception_when_create_an_employee_of_greater_than_65_or_less_than_18(){
        EmployeeRequest request = new EmployeeRequest(null, "Tom", 16, "MALE", 15000.0);

        assertThrows(InvalidAgeEmployeeException.class, () -> employeeServiceImpl.createEmployee(request));
    }

    @Test
    void should_throw_exception_when_create_an_employee_the_age_over_30_and_salary_less_than_20000(){
        EmployeeRequest request = new EmployeeRequest(null, "Tom", 30, "MALE", 15000.0);

        assertThrows(InvalidSalaryEmployeeException.class, () -> employeeServiceImpl.createEmployee(request));
    }

    @Test
    void should_create_employee_with_default_true_when_create_an_employee(){
        Employee employee = new Employee(null, "Tom", 20, "MALE", 20000.0);
        EmployeeRequest request = new EmployeeRequest(null, "Tom", 20, "MALE", 20000.0);

        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        employeeServiceImpl.createEmployee(request);
        assertTrue(employee.isActive());
    }

    @Test
    void should_set_employee_active_false_when_delete_an_employee(){
        Employee employee = new Employee(1, "John", 28, "male", 60000.0);
        assertTrue(employee.isActive());
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        employeeServiceImpl.deleteEmployee(1);
        verify(employeeRepository).save(argThat(employee1 -> !employee1.isActive()));
    }

    @Test
    void should_return_error_message_when_update_active_false_employee(){
        Employee employee = new Employee(1, "John", 20, "male", 60000.0);
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        employee.setActive(false);
        EmployeeRequest updatedEmployee = new EmployeeRequest(1, "John",28, "male", 60000.0);
        assertThrows(Exception.class, () -> {
            employeeServiceImpl.updateEmployee(1, updatedEmployee);
        });
        assertFalse(employee.isActive());
    }

}
