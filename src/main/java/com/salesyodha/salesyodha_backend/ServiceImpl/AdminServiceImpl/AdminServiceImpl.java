package com.salesyodha.salesyodha_backend.ServiceImpl.AdminServiceImpl;

import com.salesyodha.salesyodha_backend.Config.JwtService;
import com.salesyodha.salesyodha_backend.Entity.AdminEntities.AdminEntity;
import com.salesyodha.salesyodha_backend.Entity.EmployeEntity.*;
import com.salesyodha.salesyodha_backend.Reposetory.AdminReposetory.CompanyRepository;
import com.salesyodha.salesyodha_backend.Reposetory.EmployeeReposetory.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    private final PartyOrderRepository partyOrderRepository;

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
    /// GET ATTENDANCE IN BY EMPLOYEE ID
    /// =========================================
    public List<AttendanceEntity>
    getAttendanceInByEmployeeId(
            String token,
            Long employeeId
    ) {

        /// ADMIN VALIDATE
        getAdmin(token);

        EmployeeEntity employee =
                employeeRepository.findById(employeeId)

                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Employee Not Found"
                                ));

        return attendanceRepository
                .findByEmployee(employee);
    }

    /// =========================================
    /// GET ATTENDANCE OUT BY EMPLOYEE ID
    /// =========================================
    public List<AttendanceOutEntity>
    getAttendanceOutByEmployeeId(
            String token,
            Long employeeId
    ) {

        /// ADMIN VALIDATE
        getAdmin(token);

        EmployeeEntity employee =
                employeeRepository.findById(employeeId)

                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Employee Not Found"
                                ));

        return attendanceOutRepository
                .findByEmployee(employee);
    }

    /// =========================================
    /// GET PARTIES BY EMPLOYEE ID
    /// =========================================
    public List<NewPartyEntity>
    getPartiesByEmployeeId(
            String token,
            Long employeeId
    ) {

        /// ADMIN VALIDATE
        getAdmin(token);

        EmployeeEntity employee =
                employeeRepository.findById(employeeId)

                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Employee Not Found"
                                ));

        return newPartyRepository
                .findByEmployee(employee);
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


    public List<PartyOrderEntity> getPartyOrdersByPartyId(
            String token,
            Long partyId
    ) {

        // Admin validate
        getAdmin(token);

        NewPartyEntity party =
                newPartyRepository.findById(partyId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Party Not Found"
                                ));

        return partyOrderRepository.findByParty(party);
    }
}