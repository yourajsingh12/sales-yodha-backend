package com.salesyodha.salesyodha_backend.Dto.EmployeeDto;

import lombok.Data;

@Data
public class AttendanceOutRequestDTO {

    private String punchOutLocation;
    private String endReadingKm;
    private String selfieImage;
    private Double totalKm;
    private String meterImage;
}
