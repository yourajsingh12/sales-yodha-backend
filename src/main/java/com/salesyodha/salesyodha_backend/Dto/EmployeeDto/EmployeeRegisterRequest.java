package com.salesyodha.salesyodha_backend.Dto.EmployeeDto;

import lombok.Data;

@Data
public class EmployeeRegisterRequest {

    private String employeeName;
    private String mobileNumber;
    private String password;
    private String companyCode;
}
