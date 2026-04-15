package com.salesyodha.salesyodha_backend.ServiceImpl;

import com.salesyodha.salesyodha_backend.Config.JwtService;
import com.salesyodha.salesyodha_backend.Dto.AdminDto.AdminRegisterRequest;
import com.salesyodha.salesyodha_backend.Dto.AdminDto.AdminRegisterResponse;
import com.salesyodha.salesyodha_backend.Dto.ApiResponse;
import com.salesyodha.salesyodha_backend.Dto.EmployeeDto.EmployeeRegisterRequest;
import com.salesyodha.salesyodha_backend.Dto.EmployeeDto.EmployeeRegisterResponse;
import com.salesyodha.salesyodha_backend.Dto.LoginDto.LoginRequest;
import com.salesyodha.salesyodha_backend.Dto.LoginDto.LoginResponse;
import com.salesyodha.salesyodha_backend.Entity.AccessCodeEntity;
import com.salesyodha.salesyodha_backend.Entity.AdminEntities.AdminEntity;
import com.salesyodha.salesyodha_backend.Entity.EmployeEntity.EmployeeEntity;
import com.salesyodha.salesyodha_backend.Enum.Role;
import com.salesyodha.salesyodha_backend.Reposetory.AccessCodeRepository;
import com.salesyodha.salesyodha_backend.Reposetory.CompanyRepository;
import com.salesyodha.salesyodha_backend.Reposetory.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl {

    private final CompanyRepository companyRepo;
    private final EmployeeRepository employeeRepo;
    private final AccessCodeRepository accessCodeRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    //  ADMIN REGISTER
    public ApiResponse<AdminRegisterResponse> registerAdmin(AdminRegisterRequest request) {

        //  Optional access code validation
        if (request.getAccessCode() != null && !request.getAccessCode().isBlank()) {

            AccessCodeEntity accessCode = accessCodeRepo.findByCode(request.getAccessCode())
                    .orElseThrow(() -> new RuntimeException("Invalid Access Code"));

            if (accessCode.isUsed()) {
                throw new RuntimeException("Access Code already used");
            }

            accessCode.setUsed(true);
            accessCodeRepo.save(accessCode);
        }

        if (companyRepo.existsByMobileNumber(request.getMobileNumber())) {
            throw new RuntimeException("Mobile already registered");
        }

        AdminEntity company = AdminEntity.builder()
                .companyName(request.getCompanyName())
                .mobileNumber(request.getMobileNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .documentUrl(request.getDocumentUrl())
                .role(Role.ADMIN)
                .gstNumber(request.getGstNumber())
                .build();

        companyRepo.save(company);

        String token = jwtService.generateToken(
                company.getMobileNumber(),
                company.getRole().name()
        );


        return ApiResponse.<AdminRegisterResponse>builder()
                .success(true)
                .message("Company Registered Successfully")
                .statusCode(HttpStatus.CREATED.value())
                .timestamp(LocalDateTime.now())
                .data(AdminRegisterResponse.builder()
                        .companyName(company.getCompanyName())
                        .companyCode(company.getCompanyCode())
                        .mobileNumber(company.getMobileNumber())
                        .token(token)
                        .build())
                .build();
    }
    //  EMPLOYEE REGISTER
    public ApiResponse<EmployeeRegisterResponse> registerEmployee(EmployeeRegisterRequest request) {

        AdminEntity company = companyRepo.findByCompanyCode(request.getCompanyCode())
                .orElseThrow(() -> new RuntimeException("Invalid Company Code"));

        if (employeeRepo.existsByMobileNumber(request.getMobileNumber())) {
            throw new RuntimeException("Mobile already registered");
        }

        EmployeeEntity employee = EmployeeEntity.builder()
                .employeeName(request.getEmployeeName())
                .mobileNumber(request.getMobileNumber())
                .role(Role.EMPLOYEE)
                .password(passwordEncoder.encode(request.getPassword()))
                .company(company)
                .build();

        employeeRepo.save(employee);

        String token = jwtService.generateToken(
                employee.getMobileNumber(),
                employee.getRole().name()
        );

        return ApiResponse.<EmployeeRegisterResponse>builder()
                .success(true)
                .message("Employee Registered Successfully")
                .statusCode(HttpStatus.CREATED.value())
                .timestamp(LocalDateTime.now())
                .data(EmployeeRegisterResponse.builder()
                        .employeeName(employee.getEmployeeName())
                        .companyName(company.getCompanyName())
                        .mobileNumber(employee.getMobileNumber())
                        .token(token)
                        .build())
                .build();
    }

    public ApiResponse<LoginResponse> login(LoginRequest request) {

        // ADMIN CHECK
        AdminEntity admin = companyRepo.findByMobileNumber(request.getMobileNumber()).orElse(null);

        if (admin != null) {
            if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
                throw new RuntimeException("Invalid credentials");
            }

            String token = jwtService.generateToken(
                    admin.getMobileNumber(),
                    admin.getRole().name()
            );

            return ApiResponse.<LoginResponse>builder()
                    .success(true)
                    .message("Admin Login Successful")
                    .statusCode(200)
                    .timestamp(LocalDateTime.now())
                    .data(LoginResponse.builder()
                            .token(token)
                            .role(admin.getRole().name())
                            .name(admin.getCompanyName())
                            .build())
                    .build();
        }

        //  EMPLOYEE CHECK
        EmployeeEntity emp = employeeRepo.findByMobileNumber(request.getMobileNumber()).orElse(null);

        if (emp != null) {
            if (!passwordEncoder.matches(request.getPassword(), emp.getPassword())) {
                throw new RuntimeException("Invalid credentials");
            }

            String token = jwtService.generateToken(
                    emp.getMobileNumber(),
                    emp.getRole().name()
            );

            return ApiResponse.<LoginResponse>builder()
                    .success(true)
                    .message("Employee Login Successful")
                    .statusCode(200)
                    .timestamp(LocalDateTime.now())
                    .data(LoginResponse.builder()
                            .token(token)
                            .role(emp.getRole().name())
                            .name(emp.getEmployeeName())
                            .build())
                    .build();
        }

        throw new RuntimeException("User not found");
    }
}
