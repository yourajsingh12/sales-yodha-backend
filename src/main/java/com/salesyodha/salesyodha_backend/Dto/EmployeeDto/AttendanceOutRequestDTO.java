package com.salesyodha.salesyodha_backend.Dto.EmployeeDto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class AttendanceOutRequestDTO {

    @NotBlank(message = "Location is required")
    private String punchOutLocation;

    @NotNull(message = "End KM is required")
    @Min(value = 0, message = "End KM cannot be negative")
    private Integer endReadingKm;

    @NotNull(message = "Total KM is required")
    @Positive(message = "Total KM must be greater than 0")
    private Double totalKm;
}