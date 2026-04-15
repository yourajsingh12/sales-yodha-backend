package com.salesyodha.salesyodha_backend.Reposetory;

import com.salesyodha.salesyodha_backend.Entity.AccessCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccessCodeRepository extends JpaRepository<AccessCodeEntity, Long> {
    Optional<AccessCodeEntity> findByCode(String code);
    boolean existsByCode(String code);
}
