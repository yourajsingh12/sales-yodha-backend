package com.salesyodha.salesyodha_backend.Reposetory.EmployeeReposetory;

import com.salesyodha.salesyodha_backend.Entity.EmployeEntity.NewPartyEntity;
import com.salesyodha.salesyodha_backend.Entity.EmployeEntity.PartyOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartyOrderRepository
        extends JpaRepository<PartyOrderEntity, Long> {

    List<PartyOrderEntity> findByParty(
            NewPartyEntity party
    );
}
