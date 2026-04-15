package com.salesyodha.salesyodha_backend.Controller.AccessCodeController;

import com.salesyodha.salesyodha_backend.Dto.AccessCodeRequest;
import com.salesyodha.salesyodha_backend.Dto.ApiResponse;
import com.salesyodha.salesyodha_backend.ServiceImpl.AccessCodeServiceImpl.AccessCodeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/super/access")
@RequiredArgsConstructor
public class AccessCodeController {

    private final AccessCodeServiceImpl accessCodeService;

    @PostMapping("/generate")
//    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ApiResponse<String> generateCode(@RequestBody AccessCodeRequest request) {

        String code = accessCodeService.generateAccessCode(request.getValidDays());

        return ApiResponse.<String>builder()
                .success(true)
                .message("Access Code Generated Successfully")
                .statusCode(HttpStatus.CREATED.value())
                .timestamp(LocalDateTime.now())
                .data(code)
                .build();
    }
}
