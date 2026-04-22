package com.salesyodha.salesyodha_backend.Controller.EmployeeController;

import com.salesyodha.salesyodha_backend.Dto.EmployeeDto.AttendanceOutRequestDTO;
import com.salesyodha.salesyodha_backend.Dto.EmployeeDto.AttendanceRequestDTO;
import com.salesyodha.salesyodha_backend.ServiceImpl.EmployeeServiceImpl.AttendanceServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceServiceImpl attendanceService;

    @PostMapping("/punch-in")
    public ResponseEntity<String> punchIn(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody AttendanceRequestDTO requestDTO) {

        //  Bearer token extract
        String token = authHeader.substring(7);

        String response = attendanceService.punchIn(requestDTO, token);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/punch-out")
    public ResponseEntity<String> punchOut(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody AttendanceOutRequestDTO requestDTO) {

        String token = authHeader.substring(7);

        return ResponseEntity.ok(
                attendanceService.punchOut(requestDTO, token)
        );
    }
}
