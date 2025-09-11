package com.example.demo.service;

import com.example.demo.dto.CompanyRequest;
import com.example.demo.dto.CompanyResponse;
import com.example.demo.entity.Company;

import java.util.List;

public interface CompanyService {
    List<CompanyResponse> getCompanies(Integer page, Integer size);

    CompanyResponse createCompany(CompanyRequest request);

    CompanyResponse getCompanyById(int id);

    void deleteCompany(int id);

    CompanyResponse updateCompany(int id, CompanyRequest request);
}
