package com.salesyodha.salesyodha_backend.Dto.EmployeeDto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AttendanceResponseDTO {

    private Long id;
    private String location;
    private Integer km;
    private Double totalKm;
    private String selfieImage;
    private String meterImage;
    private LocalDateTime time;
}