package com.example.demo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest {
    private String name;
    private Integer age;
    @NotNull(message = "Gender cannot be null")
    private String gender;
    @Min(value = 0, message = "Salary must be positive number")
    private double salary;
    private boolean active;

    public EmployeeRequest(String gender, Integer age, String name, double salary) {
        this.salary = salary;
        this.gender = gender;
        this.age = age;
        this.name = name;
    }
}
