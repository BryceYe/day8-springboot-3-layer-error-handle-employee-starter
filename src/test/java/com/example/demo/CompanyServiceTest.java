package com.example.demo;

import com.example.demo.entity.Company;
import com.example.demo.exception.UpdateCompanyException;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.service.impl.CompanyServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CompanyServiceTest {
    @InjectMocks
    private CompanyServiceImpl companyServiceImpl;

    @Mock
    private CompanyRepository companyRepository;

    @Test
    void should_exception_when_create_a_company(){
        Company company = new Company(null, "huawei");
        when(companyRepository.createCompany(any(Company.class))).thenReturn(company);
        Company exception = companyServiceImpl.createCompany(company);
        assertEquals(exception.getName(), company.getName());
    }

    @Test
    void should_create_company_with_default_true_when_create_a_company(){
        Company company = new Company(null, "huawei");
        when(companyRepository.createCompany(any(Company.class))).thenReturn(company);
        companyServiceImpl.createCompany(company);
        assertTrue(company.isActive());
    }

    @Test
    void should_set_company_active_false_when_delete_a_company(){
        Company company = new Company(1, "huawei");
        assertTrue(company.isActive());
        when(companyRepository.getCompanyById(1)).thenReturn(company);
        companyServiceImpl.deleteCompany(1);
        verify(companyRepository).updateCompany(eq(1),argThat(company1 -> !company1.isActive()));
    }

    @Test
    void should_return_error_message_when_update_active_false_company(){
        Company company = new Company(1, "huawei");
        assertTrue(company.isActive());
        when(companyRepository.getCompanyById(1)).thenReturn(company);
        companyServiceImpl.deleteCompany(1);

        assertThrows(UpdateCompanyException.class, () -> companyServiceImpl.updateCompany(1,new Company(1, "huawei")));
    }

    @Test
    void should_return_list_when_get_companies(){
        List<Company> companies = Arrays.asList(
                new Company(1, "huawei"),
                new Company(2, "apple"));
        when(companyRepository.getCompanies(anyInt(),anyInt())).thenReturn(companies);
        List<Company> result = companyServiceImpl.getCompanies(1, 2);
        verify(companyRepository).getCompanies(eq(1),eq(2));
        assertTrue(result.get(0).getName().equals(companies.get(0).getName()));
    }
}
