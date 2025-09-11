package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest {
    private Integer id;
    private String name;
    private Integer age;
    private String gender;
    private double salary;
    private boolean active;

    public EmployeeRequest(Integer id, String gender, Integer age, String name, double salary) {
        this.salary = salary;
        this.gender = gender;
        this.age = age;
        this.name = name;
        this.id = id;
    }
}
