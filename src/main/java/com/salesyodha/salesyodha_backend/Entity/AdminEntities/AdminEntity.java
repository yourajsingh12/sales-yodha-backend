package com.salesyodha.salesyodha_backend.Entity.AdminEntities;

import com.salesyodha.salesyodha_backend.Enum.Role;
import com.salesyodha.salesyodha_backend.Enum.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "companies")
public class AdminEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false, unique = true)
    private String companyCode; // auto generated

    @Column(nullable = false, unique = true)
    private String mobileNumber;

    private String gstNumber;

    private String documentUrl;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Status status;


    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private Role role;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.status = Status.ACTIVE;
        this.role = Role.ADMIN;

        if (this.companyCode == null) {
            this.companyCode = generateCompanyCode();
        }
    }

    private String generateCompanyCode() {
        return "COMP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

}
