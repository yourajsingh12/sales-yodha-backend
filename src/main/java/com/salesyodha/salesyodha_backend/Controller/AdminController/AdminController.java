package com.salesyodha.salesyodha_backend.Controller.AdminController;

import com.salesyodha.salesyodha_backend.Entity.EmployeEntity.AttendanceEntity;
import com.salesyodha.salesyodha_backend.Entity.EmployeEntity.AttendanceOutEntity;
import com.salesyodha.salesyodha_backend.Entity.EmployeEntity.EmployeeEntity;
import com.salesyodha.salesyodha_backend.Entity.EmployeEntity.NewPartyEntity;
import com.salesyodha.salesyodha_backend.ServiceImpl.AdminServiceImpl.AdminServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminServiceImpl adminService;

    /// =========================================
    /// GET EMPLOYEES
    /// =========================================
    @GetMapping("/employees")
    public List<EmployeeEntity> getMyEmployees(

            @RequestHeader("Authorization")
            String authHeader

    ) {

        return adminService.getMyEmployees(
                extractToken(authHeader)
        );
    }

    /// =========================================
    /// GET ALL ATTENDANCE IN
    /// =========================================
    @GetMapping("/attendance-in")
    public List<AttendanceEntity>
    getAttendanceIn(

            @RequestHeader("Authorization")
            String authHeader

    ) {

        return adminService
                .getAllEmployeesAttendanceIn(

                        extractToken(authHeader)
                );
    }

    /// =========================================
    /// GET ALL ATTENDANCE OUT
    /// =========================================
    @GetMapping("/attendance-out")
    public List<AttendanceOutEntity>
    getAttendanceOut(

            @RequestHeader("Authorization")
            String authHeader

    ) {

        return adminService
                .getAllEmployeesAttendanceOut(

                        extractToken(authHeader)
                );
    }

    /// =========================================
    /// GET ALL PARTIES
    /// =========================================
    @GetMapping("/parties")
    public List<NewPartyEntity>
    getParties(

            @RequestHeader("Authorization")
            String authHeader

    ) {

        return adminService
                .getAllEmployeesParties(

                        extractToken(authHeader)
                );
    }

    /// =========================================
    /// TOKEN EXTRACT
    /// =========================================
    private String extractToken(
            String token
    ) {

        return token.replace(
                "Bearer ",
                ""
        );
    }
}