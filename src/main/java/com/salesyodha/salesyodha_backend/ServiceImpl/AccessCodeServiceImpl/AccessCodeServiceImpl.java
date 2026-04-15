package com.salesyodha.salesyodha_backend.ServiceImpl.AccessCodeServiceImpl;

import com.salesyodha.salesyodha_backend.Entity.AccessCodeEntity;
import com.salesyodha.salesyodha_backend.Reposetory.AccessCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccessCodeServiceImpl {

    private final AccessCodeRepository accessCodeRepo;

    public String generateAccessCode(int validDays) {

        String code;

        // 🔥 Unique code generate
        do {
            code = "AC-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        } while (accessCodeRepo.existsByCode(code));

        AccessCodeEntity accessCode = AccessCodeEntity.builder()
                .code(code)
                .isUsed(false)
                .createdAt(LocalDateTime.now())
                .expiryDate(LocalDateTime.now().plusDays(validDays)) // 🔥 manual days
                .build();

        accessCodeRepo.save(accessCode);

        return code;
    }
}
