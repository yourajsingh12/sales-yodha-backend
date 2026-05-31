package com.salesyodha.salesyodha_backend.Entity.EmployeEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesyodha.salesyodha_backend.Enum.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "parties")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewPartyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String partyType;

    private String partyName;

    private String shopkeeperName;

    private String contactNumber;

    private LocalDate partyDate;

    @Column(columnDefinition = "TEXT")
    private String shopAddress;

    private String shopImage;

    private String gstin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    @JsonIgnore
    private EmployeeEntity employee;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

}
