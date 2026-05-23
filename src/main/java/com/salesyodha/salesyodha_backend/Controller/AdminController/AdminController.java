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
    /// GET ATTENDANCE IN BY EMPLOYEE ID
    /// =========================================
    @GetMapping("/attendance-in/{employeeId}")
    public List<AttendanceEntity>
    getAttendanceIn(

            @RequestHeader("Authorization")
            String authHeader,

            @PathVariable
            Long employeeId

    ) {

        return adminService
                .getAttendanceInByEmployeeId(

                        extractToken(authHeader),
                        employeeId
                );
    }

    /// =========================================
    /// GET ATTENDANCE OUT BY EMPLOYEE ID
    /// =========================================
    @GetMapping("/attendance-out/{employeeId}")
    public List<AttendanceOutEntity>
    getAttendanceOut(

            @RequestHeader("Authorization")
            String authHeader,

            @PathVariable
            Long employeeId

    ) {

        return adminService
                .getAttendanceOutByEmployeeId(

                        extractToken(authHeader),
                        employeeId
                );
    }

    /// =========================================
    /// GET PARTIES BY EMPLOYEE ID
    /// =========================================
    @GetMapping("/parties/{employeeId}")
    public List<NewPartyEntity>
    getParties(

            @RequestHeader("Authorization")
            String authHeader,

            @PathVariable
            Long employeeId

    ) {

        return adminService
                .getPartiesByEmployeeId(

                        extractToken(authHeader),
                        employeeId
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