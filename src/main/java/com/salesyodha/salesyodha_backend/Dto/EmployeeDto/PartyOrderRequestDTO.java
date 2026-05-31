package com.salesyodha.salesyodha_backend.Dto.EmployeeDto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartyOrderRequestDTO {

    private Long partyId;

    private String orderType;

    private Integer quantity;

    private String flavour;

    private String remarks;

    private LocalDate orderDate;
    private LocalDate deliveryDate;
}
