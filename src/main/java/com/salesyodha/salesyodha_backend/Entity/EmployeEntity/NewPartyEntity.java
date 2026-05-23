package com.salesyodha.salesyodha_backend.Entity.EmployeEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "new_party")
public class NewPartyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /// Distributor / Retailer
    @NotBlank
    @Column(nullable = false)
    private String partyType;

    /// Party Details
    @NotBlank
    @Column(nullable = false)
    private String partyName;

    @NotBlank
    @Column(nullable = false)
    private String shopkeeperName;

    @NotBlank
    @Column(nullable = false)
    private String contactNumber;

    /// Date
    @NotNull
    @Column(nullable = false)
    private LocalDate partyDate;

    /// Address
    @Column(columnDefinition = "TEXT")
    private String shopAddress;

    /// Shop Image
    private String shopImage;

    /// Order Details
    @NotBlank
    @Column(nullable = false)
    private String orderType; // Piece / Box

    @NotNull
    @Column(nullable = false)
    private Integer quantity;

    @Column(columnDefinition = "TEXT")
    private String remarks;

    private String flavour;

    /// GST
    private String gstin;

    /// Employee Relation
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)

    @JsonIgnore
    private EmployeeEntity employee;

    /// Created Time
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
