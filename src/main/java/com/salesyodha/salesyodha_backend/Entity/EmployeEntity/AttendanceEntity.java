package com.salesyodha.salesyodha_backend.Entity.EmployeEntity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    @Column(nullable = false)
    private String punchInLocation;

    @NotNull
    @Column(nullable = false)
    private Integer startReadingKm;

    @NotBlank
    @Column(nullable = false)
    private String selfieImage;

    @NotBlank
    @Column(nullable = false)
    private String meterImage;

    @Column(nullable = false, updatable = false)
    private LocalDateTime punchInTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private EmployeeEntity employee;

    @PrePersist
    public void prePersist() {
        this.punchInTime = LocalDateTime.now();
    }
}