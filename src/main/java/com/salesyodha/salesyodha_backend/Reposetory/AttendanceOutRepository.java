package com.salesyodha.salesyodha_backend.Reposetory;

import com.salesyodha.salesyodha_backend.Entity.EmployeEntity.AttendanceOutEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceOutRepository extends JpaRepository<AttendanceOutEntity, Long> {
}
