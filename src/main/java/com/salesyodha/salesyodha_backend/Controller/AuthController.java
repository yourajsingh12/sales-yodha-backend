package com.salesyodha.salesyodha_backend.Controller;

import com.salesyodha.salesyodha_backend.Dto.AdminDto.AdminRegisterRequest;
import com.salesyodha.salesyodha_backend.Dto.AdminDto.AdminRegisterResponse;
import com.salesyodha.salesyodha_backend.Dto.ApiResponse;
import com.salesyodha.salesyodha_backend.Dto.EmployeeDto.EmployeeRegisterRequest;
import com.salesyodha.salesyodha_backend.Dto.EmployeeDto.EmployeeRegisterResponse;
import com.salesyodha.salesyodha_backend.Dto.ForgotPasswordRequest;
import com.salesyodha.salesyodha_backend.Dto.LoginDto.LoginRequest;
import com.salesyodha.salesyodha_backend.Dto.LoginDto.LoginResponse;
import com.salesyodha.salesyodha_backend.ServiceImpl.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/profile")
    public ApiResponse<?> getProfile(
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.substring(7);
        return authService.getProfile(token);
    }


    @PostMapping("/forgot-password")
    public ApiResponse<?> forgotPassword(
            @RequestBody ForgotPasswordRequest request
    ) {
        return authService.forgotPassword(request);
    }


    @PutMapping("/employee/profile")
    public ApiResponse<?> updateEmployeeProfile(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam(required = false) String employeeName,
            @RequestParam(required = false) String bloodGroup,
            @RequestParam(required = false) String fullAddress,
            @RequestParam(required = false) String reportingManager,
            @RequestParam(required = false) MultipartFile profileImage
    ) {

        String token = authHeader.substring(7);

        return authService.updateEmployeeProfile(
                token,
                employeeName,
                bloodGroup,
                fullAddress,
                reportingManager,
                profileImage
        );
    }


    @PutMapping("/company/profile")
    public ApiResponse<?> updateCompanyProfile(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam(required = false) String companyName,
            @RequestParam(required = false) String gstNumber,
            @RequestParam(required = false) MultipartFile profileImage
    ) {

        String token = authHeader.substring(7);

        return authService.updateCompanyProfile(
                token,
                companyName,
                gstNumber,
                profileImage
        );
    }

}
