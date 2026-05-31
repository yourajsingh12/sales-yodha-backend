package com.salesyodha.salesyodha_backend.Dto.EmployeeDto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartyOrderResponseDTO {

    private Long id;

    private Long partyId;

    private String partyName;

    private String orderType;

    private Integer quantity;

    private String flavour;

    private String remarks;

    private LocalDate orderDate;

    private LocalDate deliveryDate;

    private String orderStatus;

    private LocalDateTime createdAt;
}
