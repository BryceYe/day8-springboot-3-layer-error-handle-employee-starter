package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company {
    private Integer id;
    private String name;
    private boolean active = true;

    public Company(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
