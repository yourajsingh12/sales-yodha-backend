package com.salesyodha.salesyodha_backend.Dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private int statusCode;
    private LocalDateTime timestamp;
    private T data;

    ///  SUCCESS HELPER
    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .statusCode(200)
                .timestamp(LocalDateTime.now())
                .data(data)
                .build();
    }

    ///  ERROR HELPER
    public static <T> ApiResponse<T> error(String message, int code) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .statusCode(code)
                .timestamp(LocalDateTime.now())
                .data(null)
                .build();
    }
}