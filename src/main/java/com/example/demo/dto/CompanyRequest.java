package com.example.demo.dto;

import com.example.demo.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyRequest {
    private String name;
    private boolean active;

    private List<Employee> employees;

    public CompanyRequest(String name) {
        this.name = name;
    }

    public CompanyRequest(String name, List<Employee> employees) {
        this.name = name;
        this.employees = employees;
    }
}
