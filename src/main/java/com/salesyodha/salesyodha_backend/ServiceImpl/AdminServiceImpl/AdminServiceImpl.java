package com.salesyodha.salesyodha_backend.ServiceImpl.AdminServiceImpl;

import com.salesyodha.salesyodha_backend.Config.JwtService;
import com.salesyodha.salesyodha_backend.Entity.AdminEntities.AdminEntity;
import com.salesyodha.salesyodha_backend.Entity.EmployeEntity.AttendanceEntity;
import com.salesyodha.salesyodha_backend.Entity.EmployeEntity.AttendanceOutEntity;
import com.salesyodha.salesyodha_backend.Entity.EmployeEntity.EmployeeEntity;
import com.salesyodha.salesyodha_backend.Entity.EmployeEntity.NewPartyEntity;
import com.salesyodha.salesyodha_backend.Reposetory.AdminReposetory.CompanyRepository;
import com.salesyodha.salesyodha_backend.Reposetory.EmployeeReposetory.AttendanceOutRepository;
import com.salesyodha.salesyodha_backend.Reposetory.EmployeeReposetory.AttendanceRepository;
import com.salesyodha.salesyodha_backend.Reposetory.EmployeeReposetory.EmployeeRepository;
import com.salesyodha.salesyodha_backend.Reposetory.EmployeeReposetory.NewPartyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl {

    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;
    private final AttendanceRepository attendanceRepository;
    private final AttendanceOutRepository attendanceOutRepository;
    private final NewPartyRepository newPartyRepository;
    private final JwtService jwtService;

    /// =========================================
    /// GET MY EMPLOYEES
    /// =========================================
    public List<EmployeeEntity> getMyEmployees(
            String token
    ) {

        AdminEntity admin = getAdmin(token);

        return employeeRepository.findByCompanyId(
                admin.getId()
        );
    }

    /// =========================================
    /// GET ALL EMPLOYEES ATTENDANCE IN
    /// =========================================
    public List<AttendanceEntity>
    getAllEmployeesAttendanceIn(
            String token
    ) {

        AdminEntity admin = getAdmin(token);

        List<EmployeeEntity> employees =
                employeeRepository.findByCompanyId(
                        admin.getId()
                );

        List<AttendanceEntity> attendanceList =
                new ArrayList<>();

        for (EmployeeEntity employee : employees) {

            attendanceList.addAll(

                    attendanceRepository
                            .findByEmployee(employee)
            );
        }

        return attendanceList;
    }

    /// =========================================
    /// GET ALL EMPLOYEES ATTENDANCE OUT
    /// =========================================
    public List<AttendanceOutEntity>
    getAllEmployeesAttendanceOut(
            String token
    ) {

        AdminEntity admin = getAdmin(token);

        List<EmployeeEntity> employees =
                employeeRepository.findByCompanyId(
                        admin.getId()
                );

        List<AttendanceOutEntity> attendanceList =
                new ArrayList<>();

        for (EmployeeEntity employee : employees) {

            attendanceList.addAll(

                    attendanceOutRepository
                            .findByEmployee(employee)
            );
        }

        return attendanceList;
    }

    /// =========================================
    /// GET ALL EMPLOYEES PARTIES
    /// =========================================
    public List<NewPartyEntity>
    getAllEmployeesParties(
            String token
    ) {

        AdminEntity admin = getAdmin(token);

        List<EmployeeEntity> employees =
                employeeRepository.findByCompanyId(
                        admin.getId()
                );

        List<NewPartyEntity> partyList =
                new ArrayList<>();

        for (EmployeeEntity employee : employees) {

            partyList.addAll(

                    newPartyRepository
                            .findByEmployee(employee)
            );
        }

        return partyList;
    }

    /// =========================================
    /// GET ADMIN
    /// =========================================
    private AdminEntity getAdmin(
            String token
    ) {

        String phone =
                jwtService.extractPhoneNumber(token);

        return companyRepository
                .findByMobileNumber(phone)

                .orElseThrow(() ->
                        new RuntimeException(
                                "Admin not found"
                        ));
    }
}