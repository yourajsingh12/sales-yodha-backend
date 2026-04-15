package com.salesyodha.salesyodha_backend.Dto.AdminDto;

import lombok.Data;

@Data
public class AdminRegisterRequest {

    private String companyName;
    private String mobileNumber;
    private String password;
    private String documentUrl;
    private String gstNumber;
    private String accessCode;
}
