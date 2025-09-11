package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer age;
    @NotNull(message = "Gender cannot be null")
    private String gender;
    @Min(value = 0, message = "Salary must be positive number")
    private Double salary;
    private boolean active = true;

    @Column(name="company_id")
    private Integer companyId;

    public Employee(Integer id, String name, Integer age, String gender, Double salary) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
    }
}
