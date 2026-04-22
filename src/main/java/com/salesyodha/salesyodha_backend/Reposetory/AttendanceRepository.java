package com.salesyodha.salesyodha_backend.Reposetory;

import com.salesyodha.salesyodha_backend.Entity.EmployeEntity.AttendanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<AttendanceEntity, Long> {
}
