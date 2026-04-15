package com.salesyodha.salesyodha_backend.Dto.LoginDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private String token;
    private String role;
    private String name;
}
