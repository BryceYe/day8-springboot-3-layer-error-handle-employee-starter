package com.example.demo;

import com.example.demo.entity.Employee;
import com.example.demo.exception.InvalidAgeEmployeeException;
import com.example.demo.exception.InvalidSalaryEmployeeException;
import com.example.demo.exception.UpdateEmployeeException;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {
    @InjectMocks
    private EmployeeServiceImpl employeeServiceImpl;

    @Mock
    private EmployeeRepository employeeRepository;

    @Test
    void should_exception_when_create_an_employee(){
        Employee employee = new Employee(null, "Tom", 20, "MALE", 20000.0);
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

    @Test
    void should_create_employee_with_default_true_when_create_an_employee(){
        Employee employee = new Employee(null, "Tom", 20, "MALE", 20000.0);
        when(employeeRepository.createEmployee(any(Employee.class))).thenReturn(employee);
        employeeServiceImpl.createEmployee(employee);
        assertTrue(employee.isActive());
    }

    @Test
    void should_set_employee_active_false_when_delete_an_employee(){
        Employee employee = new Employee(1, "John", 28, "male", 60000.0);
        assertTrue(employee.isActive());
        when(employeeRepository.getEmployeeById(1)).thenReturn(employee);
        employeeServiceImpl.deleteEmployee(1);
        verify(employeeRepository).updateEmployee(eq(1),argThat(employee1 -> !employee1.isActive()));
    }

    @Test
    void should_return_error_message_when_update_active_false_employee(){
        Employee employee = new Employee(1, "John", 28, "male", 60000.0);
        assertTrue(employee.isActive());
        when(employeeRepository.getEmployeeById(1)).thenReturn(employee);
        employeeServiceImpl.deleteEmployee(1);

        assertThrows(UpdateEmployeeException.class, () -> employeeServiceImpl.updateEmployee(1,new Employee(1, "John", 28, "male", 65000.0)));
    }

}
