package com.salesyodha.salesyodha_backend.ServiceImpl.EmployeeServiceImpl;

import com.salesyodha.salesyodha_backend.Config.JwtService;
import com.salesyodha.salesyodha_backend.Dto.EmployeeDto.AttendanceOutRequestDTO;
import com.salesyodha.salesyodha_backend.Dto.EmployeeDto.AttendanceRequestDTO;
import com.salesyodha.salesyodha_backend.Entity.EmployeEntity.AttendanceEntity;
import com.salesyodha.salesyodha_backend.Entity.EmployeEntity.AttendanceOutEntity;
import com.salesyodha.salesyodha_backend.Entity.EmployeEntity.EmployeeEntity;
import com.salesyodha.salesyodha_backend.Reposetory.AttendanceOutRepository;
import com.salesyodha.salesyodha_backend.Reposetory.AttendanceRepository;
import com.salesyodha.salesyodha_backend.Reposetory.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl {

    private final AttendanceRepository attendanceRepository;
    private final AttendanceOutRepository attendanceOutRepository;
    private final EmployeeRepository employeeRepository;
    private final JwtService jwtService;

    public String punchIn(AttendanceRequestDTO requestDTO, String token) {

        String phone = jwtService.extractPhoneNumber(token);

        EmployeeEntity employee = employeeRepository.findByMobileNumber(phone)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        AttendanceEntity attendance = AttendanceEntity.builder()
                .employee(employee)
                .punchInLocation(requestDTO.getPunchInLocation())
                .startReadingKm(requestDTO.getStartReadingKm())
                .selfieImage(requestDTO.getSelfieImage())
                .meterImage(requestDTO.getMeterImage())
                .build();

        attendanceRepository.save(attendance);

        return "Punch In Successful";
    }

    public String punchOut(AttendanceOutRequestDTO requestDTO, String token) {

        String phone = jwtService.extractPhoneNumber(token);

        EmployeeEntity employee = employeeRepository.findByMobileNumber(phone)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (requestDTO.getTotalKm() == null || requestDTO.getTotalKm() < 0) {
            throw new RuntimeException("Invalid total KM");
        }

        AttendanceOutEntity attendanceOut = AttendanceOutEntity.builder()
                .employee(employee)
                .punchOutLocation(requestDTO.getPunchOutLocation())
                .endReadingKm(requestDTO.getEndReadingKm())
                .totalKm(requestDTO.getTotalKm())
                .selfieImage(requestDTO.getSelfieImage())
                .meterImage(requestDTO.getMeterImage())
                .build();

        attendanceOutRepository.save(attendanceOut);

        return "Punch Out Successful";
    }
}
