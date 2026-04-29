package com.salesyodha.salesyodha_backend.Controller.AdminController;

import com.salesyodha.salesyodha_backend.Entity.EmployeEntity.EmployeeEntity;
import com.salesyodha.salesyodha_backend.ServiceImpl.AdminServiceImpl.AdminServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminServiceImpl adminService;

    @GetMapping("/employees")
    public List<EmployeeEntity> getMyEmployees(
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);

        return adminService.getMyEmployees(token);
    }
}
