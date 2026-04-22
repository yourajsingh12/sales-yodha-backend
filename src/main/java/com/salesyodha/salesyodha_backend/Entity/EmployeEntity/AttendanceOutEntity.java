package com.salesyodha.salesyodha_backend.Entity.EmployeEntity;

import jakarta.persistence.*;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@Entity
@Table(name = "attendance_out")
public class AttendanceOutEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String punchOutLocation;
    private String endReadingKm;

    @Column(name = "total_km")
    private Double totalKm;

    private String selfieImage;
    private String meterImage;

    private LocalDateTime punchOutTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private EmployeeEntity employee;

    @PrePersist
    public void prePersist() {
        this.punchOutTime = LocalDateTime.now();
    }
}
