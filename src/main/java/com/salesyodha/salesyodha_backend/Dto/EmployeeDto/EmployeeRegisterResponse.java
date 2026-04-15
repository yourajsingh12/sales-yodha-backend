package com.salesyodha.salesyodha_backend.Dto.EmployeeDto;

import lombok.*;

@Data
@Builder
public class EmployeeRegisterResponse {

    private String employeeName;
    private String companyName;
    private String mobileNumber;
    private String token;
}

