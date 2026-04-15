package com.salesyodha.salesyodha_backend.Controller;

import com.salesyodha.salesyodha_backend.Dto.AdminDto.AdminRegisterRequest;
import com.salesyodha.salesyodha_backend.Dto.AdminDto.AdminRegisterResponse;
import com.salesyodha.salesyodha_backend.Dto.ApiResponse;
import com.salesyodha.salesyodha_backend.Dto.EmployeeDto.EmployeeRegisterRequest;
import com.salesyodha.salesyodha_backend.Dto.EmployeeDto.EmployeeRegisterResponse;
import com.salesyodha.salesyodha_backend.Dto.LoginDto.LoginRequest;
import com.salesyodha.salesyodha_backend.Dto.LoginDto.LoginResponse;
import com.salesyodha.salesyodha_backend.ServiceImpl.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authService;

    @PostMapping("/admin/register")
    public ApiResponse<AdminRegisterResponse> registerAdmin(@RequestBody AdminRegisterRequest request) {
        return authService.registerAdmin(request);
    }

    @PostMapping("/employee/register")
    public ApiResponse<EmployeeRegisterResponse> registerEmployee(@RequestBody EmployeeRegisterRequest request) {
        return authService.registerEmployee(request);
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

}
