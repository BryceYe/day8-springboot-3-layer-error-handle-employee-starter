package com.example.demo.service.impl;

import com.example.demo.entity.Company;
import com.example.demo.exception.UpdateCompanyException;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;

    public List<Company> getCompanies(Integer page, Integer size) {
        return companyRepository.getCompanies(page, size);
    }

    @Override
    public Company createCompany(Company company) {
        return companyRepository.createCompany(company);
    }

    @Override
    public Company getCompanyById(int id) {
        Company company = companyRepository.getCompanyById(id);
        if(company == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found with id: " + id);
        }
        return company;
    }

    @Override
    public void deleteCompany(int id) {
        Company company = getCompanyById(id);
        company.setActive(false);
        companyRepository.updateCompany(id, company);
    }

    @Override
    public Company updateCompany(int id, Company updatedCompany) {
        Company found = companyRepository.getCompanyById(id);
        if(found == null) {
            throw new UpdateCompanyException("Company not found");
        }
        if(!found.isActive()) {
            throw new UpdateCompanyException("Company's active is false");
        }
        return companyRepository.updateCompany(id, updatedCompany);
    }

    @Override
    public void empty() {
        companyRepository.empty();
    }
}
