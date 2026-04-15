package com.salesyodha.salesyodha_backend.Exception;

import com.salesyodha.salesyodha_backend.Dto.ApiResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ✅ 1. Business / Runtime Errors
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Object>> handleRuntimeException(RuntimeException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.builder()
                        .success(false)
                        .message(ex.getMessage()) // 🔥 actual message
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .timestamp(LocalDateTime.now())
                        .data(null)
                        .build());
    }

    // ✅ 2. Validation Errors (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationException(MethodArgumentNotValidException ex) {

        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.builder()
                        .success(false)
                        .message(errorMessage)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .timestamp(LocalDateTime.now())
                        .data(null)
                        .build());
    }

    // ✅ 3. Database Errors (Unique, Null, Constraint)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Object>> handleDatabaseException(DataIntegrityViolationException ex) {

        String message = "Database error";

        if (ex.getMostSpecificCause().getMessage().contains("mobile_number")) {
            message = "Mobile number already exists";
        }

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponse.builder()
                        .success(false)
                        .message(message)
                        .statusCode(HttpStatus.CONFLICT.value())
                        .timestamp(LocalDateTime.now())
                        .data(null)
                        .build());
    }

    // ✅ 4. Illegal Argument
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Object>> handleIllegalArgument(IllegalArgumentException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.builder()
                        .success(false)
                        .message(ex.getMessage())
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .timestamp(LocalDateTime.now())
                        .data(null)
                        .build());
    }

    // ✅ 5. GLOBAL ERROR (LAST FALLBACK)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGlobalException(Exception ex) {

        // 🔥 IMPORTANT: log error
        ex.printStackTrace();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.builder()
                        .success(false)
                        .message("Internal server error") // 🔒 hide sensitive info
                        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .timestamp(LocalDateTime.now())
                        .data(null)
                        .build());
    }
}