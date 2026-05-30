package com.salesyodha.salesyodha_backend.Reposetory.EmployeeReposetory;

import com.salesyodha.salesyodha_backend.Entity.EmployeEntity.AttendanceOutEntity;
import com.salesyodha.salesyodha_backend.Entity.EmployeEntity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AttendanceOutRepository extends JpaRepository<AttendanceOutEntity, Long> {
    List<AttendanceOutEntity> findByEmployee(EmployeeEntity employee);
    List<AttendanceOutEntity> findByEmployeeId(Long employeeId);
    Optional<AttendanceOutEntity> findTopByEmployeeOrderByPunchOutTimeDesc(
            EmployeeEntity employee
    );
}
