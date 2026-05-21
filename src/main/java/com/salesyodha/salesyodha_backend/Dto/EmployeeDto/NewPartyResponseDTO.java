package com.salesyodha.salesyodha_backend.Dto.EmployeeDto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewPartyResponseDTO {

    private Long id;

    private String partyType;

    private String partyName;

    private String shopkeeperName;

    private String contactNumber;

    private LocalDate partyDate;

    private String shopAddress;

    private String shopImage;

    private String orderType;

    private Integer quantity;

    private String remarks;

    private String flavour;

    private String gstin;

    private LocalDateTime createdAt;
}
