package com.salesyodha.salesyodha_backend.Config;

import com.salesyodha.salesyodha_backend.Entity.AdminEntities.AdminEntity;
import com.salesyodha.salesyodha_backend.Entity.EmployeEntity.EmployeeEntity;
import com.salesyodha.salesyodha_backend.Reposetory.CompanyRepository;
import com.salesyodha.salesyodha_backend.Reposetory.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final CompanyRepository companyRepo;
    private final EmployeeRepository employeeRepo;

    @Override
    public UserDetails loadUserByUsername(String mobile) throws UsernameNotFoundException {

        // 🔹 Check Admin
        AdminEntity admin = companyRepo.findByMobileNumber(mobile).orElse(null);
        if (admin != null) {
            return new User(admin.getMobileNumber(), admin.getPassword(), Collections.emptyList());
        }

        // 🔹 Check Employee
        EmployeeEntity emp = employeeRepo.findByMobileNumber(mobile).orElse(null);
        if (emp != null) {
            return new User(emp.getMobileNumber(), emp.getPassword(), Collections.emptyList());
        }

        throw new UsernameNotFoundException("User not found");
    }
}
