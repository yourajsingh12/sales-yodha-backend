package com.salesyodha.salesyodha_backend.Entity.EmployeEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesyodha.salesyodha_backend.Entity.AdminEntities.AdminEntity;
import com.salesyodha.salesyodha_backend.Enum.Role;
import com.salesyodha.salesyodha_backend.Enum.Status;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "employees")
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String employeeName;

    @Column(nullable = false, unique = true)
    private String mobileNumber;

    @Column(nullable = false)
    private String password;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private AdminEntity company;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private Role role;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.status = Status.ACTIVE;
        this.role = Role.EMPLOYEE;
    }
}
