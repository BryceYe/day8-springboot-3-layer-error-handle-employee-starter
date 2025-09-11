package com.example.demo;

import com.example.demo.dto.CompanyRequest;
import com.example.demo.dto.CompanyResponse;
import com.example.demo.entity.Company;
import com.example.demo.entity.Employee;
import com.example.demo.exception.UpdateCompanyException;
import com.example.demo.repository.ICompanyRepository;
import com.example.demo.service.impl.CompanyServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CompanyServiceTest {
    @InjectMocks
    private CompanyServiceImpl companyServiceImpl;

    @Mock
    private ICompanyRepository companyRepository;

    @Test
    void should_exception_when_create_a_company(){


        List<Employee> employees = Arrays.asList(
                new Employee(1, "Jack", 18, "male", 1000.0),
                new Employee(2, "Mike", 18, "male", 1000.0));
        Company company = new Company(1, "huawei", employees);
        when(companyRepository.save(any(Company.class))).thenReturn(company);

        CompanyRequest request = new CompanyRequest("huawei", employees);

        CompanyResponse exception = companyServiceImpl.createCompany(request);

        assertEquals(exception.getName(), company.getName());
        assertEquals(exception.getEmployees().get(0).getName(), employees.get(0).getName());
    }

    @Test
    void should_create_company_with_default_true_when_create_a_company(){
        Company company = new Company(null, "huawei");
        when(companyRepository.save(any(Company.class))).thenReturn(company);

        CompanyRequest request = new CompanyRequest("huawei");
        CompanyResponse response = companyServiceImpl.createCompany(request);
        assertTrue(response.isActive());
    }

    @Test
    void should_set_company_active_false_when_delete_a_company(){
        Company company = new Company(1, "huawei");
        assertTrue(company.isActive());
        when(companyRepository.findById(1)).thenReturn(Optional.of(company));
        companyServiceImpl.deleteCompany(1);
        verify(companyRepository).save(argThat(company1 -> !company1.isActive()));
    }

    @Test
    void should_return_error_message_when_update_active_false_company(){
        CompanyRequest request = new CompanyRequest("huawei");

        assertThrows(UpdateCompanyException.class, () -> companyServiceImpl.updateCompany(1,request));
    }

}
