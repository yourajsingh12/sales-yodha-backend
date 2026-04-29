package com.salesyodha.salesyodha_backend.Reposetory.EmployeeReposetory;

import com.salesyodha.salesyodha_backend.Entity.EmployeEntity.AttendanceOutEntity;
import com.salesyodha.salesyodha_backend.Entity.EmployeEntity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendanceOutRepository extends JpaRepository<AttendanceOutEntity, Long> {
    List<AttendanceOutEntity> findByEmployee(EmployeeEntity employee);
    List<AttendanceOutEntity> findByEmployeeId(Long employeeId);
}
