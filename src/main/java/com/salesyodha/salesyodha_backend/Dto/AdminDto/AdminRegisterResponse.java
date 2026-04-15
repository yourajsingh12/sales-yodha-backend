package com.salesyodha.salesyodha_backend.Dto.AdminDto;

import lombok.*;

@Data
@Builder
public class AdminRegisterResponse {

    private String companyName;
    private String companyCode;
    private String mobileNumber;
    private String token;
}

