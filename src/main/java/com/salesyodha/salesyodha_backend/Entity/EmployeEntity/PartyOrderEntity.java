package com.salesyodha.salesyodha_backend.Entity.EmployeEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesyodha.salesyodha_backend.Enum.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "party_orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartyOrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "party_id")
    @JsonIgnore
    private NewPartyEntity party;

    private String orderType;

    private Integer quantity;

    private String flavour;

    @Column(columnDefinition = "TEXT")
    private String remarks;

    private LocalDate orderDate;

    private LocalDate deliveryDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {

        createdAt = LocalDateTime.now();

        if(orderStatus == null){
            orderStatus = OrderStatus.PENDING;
        }

        if(orderDate == null){
            orderDate = LocalDate.now();
        }
    }

}
