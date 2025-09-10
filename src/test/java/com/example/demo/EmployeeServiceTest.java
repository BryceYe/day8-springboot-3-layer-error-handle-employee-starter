package com.example.demo;

import com.example.demo.entity.Employee;
import com.example.demo.exception.InvalidAgeEmployeeException;
import com.example.demo.exception.InvalidSalaryEmployeeException;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {
    @InjectMocks
    private EmployeeServiceImpl employeeServiceImpl;

    @Mock
    private EmployeeRepository employeeRepository;

    @Test
    void should_exception_when_create_an_employee(){
        Employee employee = new Employee(null, "Tom", 20, "MALE", 20000.0);
        employeeServiceImpl.createEmployee(employee);
        when(employeeRepository.createEmployee(any(Employee.class))).thenReturn(employee);
        Employee employeeResult = employeeServiceImpl.createEmployee(employee);
        assertEquals(employeeResult.getAge(), employee.getAge());
    }

    @Test
    void should_throw_exception_when_create_an_employee_of_greater_than_65_or_less_than_18(){
        Employee employee = new Employee(null, "Tom", 16, "MALE", 20000.0);
        when(employeeRepository.createEmployee(any(Employee.class))).thenReturn(employee);

        assertThrows(InvalidAgeEmployeeException.class, () -> employeeServiceImpl.createEmployee(employee));
    }

    @Test
    void should_throw_exception_when_create_an_employee_the_age_over_30_and_salary_less_than_20000(){
        Employee employee = new Employee(null, "Tom", 30, "MALE", 15000.0);

        assertThrows(InvalidSalaryEmployeeException.class, () -> employeeServiceImpl.createEmployee(employee));
    }
}
