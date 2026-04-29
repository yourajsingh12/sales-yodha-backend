package com.salesyodha.salesyodha_backend.Reposetory.EmployeeReposetory;

import com.salesyodha.salesyodha_backend.Entity.EmployeEntity.AttendanceEntity;
import com.salesyodha.salesyodha_backend.Entity.EmployeEntity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendanceRepository extends JpaRepository<AttendanceEntity, Long> {
    List<AttendanceEntity> findByEmployee(EmployeeEntity employee);
    List<AttendanceEntity> findByEmployeeId(Long employeeId);
}
