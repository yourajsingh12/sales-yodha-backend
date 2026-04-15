package com.salesyodha.salesyodha_backend.Dto.LoginDto;

import lombok.Data;

@Data
public class LoginRequest {
    private String mobileNumber;
    private String password;
}
