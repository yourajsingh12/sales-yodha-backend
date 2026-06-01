package com.salesyodha.salesyodha_backend.ServiceImpl.EmployeeServiceImpl;

import com.salesyodha.salesyodha_backend.Config.JwtService;
import com.salesyodha.salesyodha_backend.Dto.ApiResponse;
import com.salesyodha.salesyodha_backend.Dto.EmployeeDto.AttendanceResponseDTO;
import com.salesyodha.salesyodha_backend.Entity.EmployeEntity.*;
import com.salesyodha.salesyodha_backend.Reposetory.EmployeeReposetory.AttendanceOutRepository;
import com.salesyodha.salesyodha_backend.Reposetory.EmployeeReposetory.AttendanceRepository;
import com.salesyodha.salesyodha_backend.Reposetory.EmployeeReposetory.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl {

    private final AttendanceRepository attendanceRepository;
    private final AttendanceOutRepository attendanceOutRepository;
    private final EmployeeRepository employeeRepository;
    private final JwtService jwtService;






    ///  PUNCH IN
    public ApiResponse<?> punchIn(
            String location,
            String beatPlan,
            Integer startKm,
            MultipartFile selfie,
            MultipartFile meter,
            String token
    ) {

        EmployeeEntity employee = getEmployee(token);


        LocalDate today = LocalDate.now(
                ZoneId.of("Asia/Kolkata")
        );

        System.out.println("TODAY = " + today);

        attendanceRepository.findByEmployee(employee)
                .forEach(a -> {
                    System.out.println(
                            "IN DATE = " +
                                    a.getPunchInTime().toLocalDate()
                    );
                });

        System.out.println(
                "EMPLOYEE ID = " +
                        employee.getId()
        );


        boolean alreadyPunchedIn = attendanceRepository
                .findByEmployee(employee)
                .stream()
                .anyMatch(a ->
                        a.getPunchInTime().toLocalDate().equals(today)
                );

        if (alreadyPunchedIn) {
            return ApiResponse.error(
                    "Attendance already punched in today",
                    400
            );
        }

        if (startKm == null || startKm < 0) {
            return ApiResponse.error("Invalid Start KM", 400);
        }

        String selfiePath = saveFile(selfie);
        String meterPath = saveFile(meter);

        AttendanceEntity attendance = AttendanceEntity.builder()
                .employee(employee)
                .punchInLocation(location)
                .beatPlan(beatPlan)
                .startReadingKm(startKm)
                .selfieImage(selfiePath)
                .meterImage(meterPath)
                .build();

        attendanceRepository.save(attendance);

        ///  DTO MAPPING
        AttendanceResponseDTO response = AttendanceResponseDTO.builder()
                .id(attendance.getId())
                .location(attendance.getPunchInLocation())
                .beatPlan(attendance.getBeatPlan())
                .km(attendance.getStartReadingKm())
                .selfieImage(attendance.getSelfieImage())
                .meterImage(attendance.getMeterImage())
                .time(attendance.getPunchInTime())
                .build();

        return ApiResponse.success("Punch In Successful", response);
    }

    ///  PUNCH OUT
    public ApiResponse<?> punchOut(
            String location,
            Integer endKm,
            Double totalKm,
            MultipartFile selfie,
            MultipartFile meter,
            String token
    ) {

        EmployeeEntity employee = getEmployee(token);

        LocalDate today = LocalDate.now(
                ZoneId.of("Asia/Kolkata")
        );


        System.out.println("TODAY = " + today);

        attendanceRepository.findByEmployee(employee)
                .forEach(a -> {
                    System.out.println(
                            "IN DATE = " +
                                    a.getPunchInTime().toLocalDate()
                    );
                });

        System.out.println(
                "EMPLOYEE ID = " +
                        employee.getId()
        );


        boolean alreadyPunchedOut = attendanceOutRepository
                .findByEmployee(employee)
                .stream()
                .anyMatch(a ->
                        a.getPunchOutTime().toLocalDate().equals(today)
                );

        if (alreadyPunchedOut) {
            return ApiResponse.error(
                    "Attendance already punched out today",
                    400
            );
        }

        boolean punchedInToday = attendanceRepository
                .findByEmployee(employee)
                .stream()
                .anyMatch(a ->
                        a.getPunchInTime().toLocalDate().equals(today)
                );

        if (!punchedInToday) {
            return ApiResponse.error(
                    "Please punch in first",
                    400
            );
        }





        if (totalKm == null || totalKm < 0) {
            return ApiResponse.error("Invalid total KM", 400);
        }

        String selfiePath = saveFile(selfie);
        String meterPath = saveFile(meter);

        AttendanceOutEntity attendanceOut = AttendanceOutEntity.builder()
                .employee(employee)
                .punchOutLocation(location)
                .endReadingKm(endKm)
                .totalKm(totalKm)
                .selfieImage(selfiePath)
                .meterImage(meterPath)
                .build();

        attendanceOutRepository.save(attendanceOut);

        ///  DTO MAPPING
        AttendanceResponseDTO response = AttendanceResponseDTO.builder()
                .id(attendanceOut.getId())
                .location(attendanceOut.getPunchOutLocation())
                .km(attendanceOut.getEndReadingKm())
                .totalKm(attendanceOut.getTotalKm())
                .selfieImage(attendanceOut.getSelfieImage())
                .meterImage(attendanceOut.getMeterImage())
                .time(attendanceOut.getPunchOutTime())
                .build();

        return ApiResponse.success("Punch Out Successful", response);
    }



    ///  COMMON METHOD
    private EmployeeEntity getEmployee(String token) {
        String phone = jwtService.extractPhoneNumber(token);
        return employeeRepository.findByMobileNumber(phone)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    ///  FILE SAVE (SAFE VERSION)
    private String saveFile(MultipartFile file) {

        try {

            if (file == null || file.isEmpty()) {
                throw new RuntimeException("File is empty");
            }

            // ✅ Allow only images
            String contentType = file.getContentType();

            if (contentType == null ||
                    !(contentType.equals("image/jpeg")
                            || contentType.equals("image/png")
                            || contentType.equals("image/jpg"))) {

                throw new RuntimeException("Only JPG, JPEG, PNG allowed");
            }

            //  Docker container path
            String folder = System.getProperty("user.dir") + "/uploads/";

            java.io.File dir = new java.io.File(folder);

            if (!dir.exists()) {
                dir.mkdirs();
            }

            String original = file.getOriginalFilename();

            String ext = (original != null && original.contains("."))
                    ? original.substring(original.lastIndexOf("."))
                    : ".jpg";

            String fileName = System.currentTimeMillis() + ext;

            String filePath = folder + fileName;

            System.out.println("Saving file to: " + filePath);

            file.transferTo(new java.io.File(filePath));

            return "uploads/" + fileName;

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException("File upload failed");
        }
    }


    public ApiResponse<?> getAllAttendance(String token) {

        EmployeeEntity employee = getEmployee(token);

        List<AttendanceEntity> inList = attendanceRepository.findByEmployee(employee);
        List<AttendanceOutEntity> outList = attendanceOutRepository.findByEmployee(employee);

        ///  MAP PUNCH IN
        List<AttendanceResponseDTO> punchInList = inList.stream().map(a ->
                AttendanceResponseDTO.builder()
                        .id(a.getId())
                        .location(a.getPunchInLocation())
                        .beatPlan(a.getBeatPlan())
                        .km(a.getStartReadingKm())
                        .selfieImage(a.getSelfieImage())
                        .meterImage(a.getMeterImage())
                        .time(a.getPunchInTime())
                        .build()
        ).toList();

        /// 🔁 MAP PUNCH OUT
        List<AttendanceResponseDTO> punchOutList = outList.stream().map(a ->
                AttendanceResponseDTO.builder()
                        .id(a.getId())
                        .location(a.getPunchOutLocation())
                        .km(a.getEndReadingKm())
                        .totalKm(a.getTotalKm())
                        .selfieImage(a.getSelfieImage())
                        .meterImage(a.getMeterImage())
                        .time(a.getPunchOutTime())
                        .build()
        ).toList();

        ///  FINAL RESPONSE
        Map<String, Object> data = new HashMap<>();
        data.put("punchIn", punchInList);
        data.put("punchOut", punchOutList);

        return ApiResponse.success("Attendance fetched", data);
    }


    public ApiResponse<?> getTodayStatus(String token) {

        EmployeeEntity employee = getEmployee(token);

        LocalDate today = LocalDate.now(
                ZoneId.of("Asia/Kolkata")
        );

        AttendanceEntity attendanceIn = attendanceRepository
                .findByEmployee(employee)
                .stream()
                .filter(a ->
                        a.getPunchInTime()
                                .toLocalDate()
                                .equals(today))
                .findFirst()
                .orElse(null);

        AttendanceOutEntity attendanceOut = attendanceOutRepository
                .findByEmployee(employee)
                .stream()
                .filter(a ->
                        a.getPunchOutTime()
                                .toLocalDate()
                                .equals(today))
                .findFirst()
                .orElse(null);

        Map<String, Object> data = new HashMap<>();

        data.put("punchedIn", attendanceIn != null);
        data.put("punchedOut", attendanceOut != null);

        data.put(
                "startKm",
                attendanceIn != null
                        ? attendanceIn.getStartReadingKm()
                        : null
        );

        return ApiResponse.success(
                "Today's attendance status",
                data
        );
    }


}