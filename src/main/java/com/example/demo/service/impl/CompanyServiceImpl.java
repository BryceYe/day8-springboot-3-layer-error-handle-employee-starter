package com.example.demo.service.impl;

import com.example.demo.dto.CompanyRequest;
import com.example.demo.dto.CompanyResponse;
import com.example.demo.dto.mapper.CompanyMapper;
import com.example.demo.entity.Company;
import com.example.demo.exception.UpdateCompanyException;
import com.example.demo.repository.ICompanyRepository;
import com.example.demo.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final ICompanyRepository companyRepository;

    public List<CompanyResponse> getCompanies(Integer page, Integer size) {
        if(page == null || size == null){
            return CompanyMapper.toResponse(companyRepository.findAll());
        } else {
            Pageable pageable = PageRequest.of(page - 1, size);
            return CompanyMapper.toResponse(companyRepository.findAll(pageable).toList());
        }
    }

    @Override
    public CompanyResponse createCompany(CompanyRequest request) {
        Company company = new Company();
        BeanUtils.copyProperties(request, company);
        return CompanyMapper.toResponse(companyRepository.save(company));
    }

    @Override
    public CompanyResponse getCompanyById(int id) {
        Optional<Company> company = companyRepository.findById(id);
        if(company.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found with id: " + id);
        }
        return CompanyMapper.toResponse(company.orElse(null));
    }

    @Override
    public void deleteCompany(int id) {
        Optional<Company> company = companyRepository.findById(id);
        if(company.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found with id: " + id);
        }
        Company entity = company.get();
        entity.setActive(false);
        companyRepository.save(entity);
    }

    @Override
    public CompanyResponse updateCompany(int id, CompanyRequest request) {
        Optional<Company> company = companyRepository.findById(id);
        if(company.isEmpty()) {
            throw new UpdateCompanyException("Company not found");
        }
        if(!company.get().isActive()) {
            throw new UpdateCompanyException("Company's active is false");
        }
        Company updatedCompany = new Company();
        BeanUtils.copyProperties(request, updatedCompany);
        updatedCompany.setId(id);
        return CompanyMapper.toResponse(companyRepository.save(updatedCompany));
    }
}
