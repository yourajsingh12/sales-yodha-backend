package com.salesyodha.salesyodha_backend.Reposetory.AdminReposetory;

import com.salesyodha.salesyodha_backend.Entity.AdminEntities.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<AdminEntity, Long> {
    Optional<AdminEntity> findByCompanyCode(String code);
    Optional<AdminEntity> findByMobileNumber(String mobile);
    boolean existsByMobileNumber(String mobileNumber);
}
