package com.salesyodha.salesyodha_backend.Dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileResponseDTO {

    private String role;

    // 🔹 COMMON
    private String mobileNumber;

    // 🔹 ADMIN
    private String companyName;
    private String documentUrl;
    private String gstNumber;

    // 🔹 EMPLOYEE
    private String employeeName;
    private String companyCode;
}
