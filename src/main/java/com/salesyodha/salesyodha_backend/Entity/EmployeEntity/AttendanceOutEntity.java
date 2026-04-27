package com.salesyodha.salesyodha_backend.Entity.EmployeEntity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "attendance_out")
public class AttendanceOutEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String punchOutLocation;

    @NotNull
    @Column(nullable = false)
    private Integer endReadingKm;

    @NotNull
    @Column(name = "total_km", nullable = false)
    private Double totalKm;

    @NotBlank
    @Column(nullable = false)
    private String selfieImage;

    @NotBlank
    @Column(nullable = false)
    private String meterImage;

    @Column(nullable = false, updatable = false)
    private LocalDateTime punchOutTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private EmployeeEntity employee;

    @PrePersist
    public void prePersist() {
        this.punchOutTime = LocalDateTime.now();
    }
}