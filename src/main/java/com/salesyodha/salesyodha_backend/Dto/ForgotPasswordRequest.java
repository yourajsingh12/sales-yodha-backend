package com.salesyodha.salesyodha_backend.Dto;

import lombok.Data;

@Data
public class ForgotPasswordRequest {

    private String mobileNumber;

    private String newPassword;
}
