package com.salesyodha.salesyodha_backend.Dto.EmployeeDto;

import lombok.Data;

@Data
public class AttendanceRequestDTO {

    private String punchInLocation;
    private Integer startReadingKm;
}