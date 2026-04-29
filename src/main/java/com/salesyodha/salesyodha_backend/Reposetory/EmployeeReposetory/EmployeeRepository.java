package com.salesyodha.salesyodha_backend.Reposetory.EmployeeReposetory;

import com.salesyodha.salesyodha_backend.Entity.EmployeEntity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
    boolean existsByMobileNumber(String mobileNumber);
    Optional<EmployeeEntity> findByMobileNumber(String mobile);
    List<EmployeeEntity> findByCompanyId(Long companyId);
}
