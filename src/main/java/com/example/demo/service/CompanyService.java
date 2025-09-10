package com.example.demo.service;

import com.example.demo.entity.Company;

import java.util.List;

public interface CompanyService {
    List<Company> getCompanies(Integer page, Integer size);

    Company createCompany(Company company);

    Company getCompanyById(int id);

    void deleteCompany(int id);

    Company updateCompany(int id, Company updatedCompany);

    void empty();
}
