package com.salesyodha.salesyodha_backend.Reposetory.EmployeeReposetory;

import com.salesyodha.salesyodha_backend.Entity.EmployeEntity.EmployeeEntity;
import com.salesyodha.salesyodha_backend.Entity.EmployeEntity.NewPartyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewPartyRepository
        extends JpaRepository<NewPartyEntity, Long> {

    List<NewPartyEntity> findByEmployee(EmployeeEntity employee);
}
