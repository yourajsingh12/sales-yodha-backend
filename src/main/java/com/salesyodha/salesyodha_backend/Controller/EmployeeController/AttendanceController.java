package com.salesyodha.salesyodha_backend.Controller.EmployeeController;

import com.salesyodha.salesyodha_backend.Dto.ApiResponse;
import com.salesyodha.salesyodha_backend.Dto.EmployeeDto.AttendanceRequestDTO;
import com.salesyodha.salesyodha_backend.ServiceImpl.EmployeeServiceImpl.AttendanceServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceServiceImpl attendanceService;

    ///  PUNCH IN
    @PostMapping("/punch-in")
    public ResponseEntity<ApiResponse<?>> punchIn(
            @RequestHeader("Authorization") String authHeader,
            @RequestPart("data") @Valid AttendanceRequestDTO dto,
            @RequestPart("selfieImage") MultipartFile selfieImage,
            @RequestPart("meterImage") MultipartFile meterImage
    ) {

        String token = extractToken(authHeader);

        return ResponseEntity.ok(
                attendanceService.punchIn(
                        dto.getPunchInLocation(),
                        dto.getStartReadingKm(),
                        selfieImage,
                        meterImage,
                        token
                )
        );
    }

    ///  PUNCH OUT
    @PostMapping("/punch-out")
    public ResponseEntity<ApiResponse<?>> punchOut(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam String punchOutLocation,
            @RequestParam Integer endReadingKm,
            @RequestParam Double totalKm,
            @RequestParam MultipartFile selfieImage,
            @RequestParam MultipartFile meterImage
    ) {

        String token = extractToken(authHeader);

        return ResponseEntity.ok(
                attendanceService.punchOut(
                        punchOutLocation,
                        endReadingKm,
                        totalKm,
                        selfieImage,
                        meterImage,
                        token
                )
        );
    }

    /// GET ALL ATTENDANCE
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<?>> getAllAttendance(
            @RequestHeader("Authorization") String authHeader
    ) {

        String token = extractToken(authHeader);

        return ResponseEntity.ok(
                attendanceService.getAllAttendance(token)
        );
    }

    ///  TOKEN SAFE
    private String extractToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid token");
        }
        return authHeader.substring(7);
    }
}