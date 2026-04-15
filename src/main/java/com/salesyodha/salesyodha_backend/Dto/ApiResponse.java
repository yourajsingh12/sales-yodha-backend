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
}
