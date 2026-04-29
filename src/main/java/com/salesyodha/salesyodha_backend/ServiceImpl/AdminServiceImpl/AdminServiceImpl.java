package com.salesyodha.salesyodha_backend.ServiceImpl.AdminServiceImpl;

import com.salesyodha.salesyodha_backend.Config.JwtService;
import com.salesyodha.salesyodha_backend.Entity.AdminEntities.AdminEntity;
import com.salesyodha.salesyodha_backend.Entity.EmployeEntity.EmployeeEntity;
import com.salesyodha.salesyodha_backend.Reposetory.AdminReposetory.CompanyRepository;
import com.salesyodha.salesyodha_backend.Reposetory.EmployeeReposetory.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl {

    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;
    private final JwtService jwtService;

    public List<EmployeeEntity> getMyEmployees(String token) {

        String phone = jwtService.extractPhoneNumber(token);

        AdminEntity admin = companyRepository.findByMobileNumber(phone)
                .orElseThrow(() -> new RuntimeException("Admin not found"));


        return employeeRepository.findByCompanyId(admin.getId());
    }
}