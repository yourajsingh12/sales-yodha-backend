package com.salesyodha.salesyodha_backend.Entity.EmployeEntity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "attendance_in")
public class AttendanceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String punchInLocation;

    private String startReadingKm;

    private String selfieImage;

    private String meterImage;

    private LocalDateTime punchInTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private EmployeeEntity employee;

    @PrePersist
    public void prePersist() {
        this.punchInTime = LocalDateTime.now();
    }
}