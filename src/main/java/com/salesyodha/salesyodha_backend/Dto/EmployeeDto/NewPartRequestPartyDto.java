package com.salesyodha.salesyodha_backend.Dto.EmployeeDto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewPartRequestPartyDto {

    /// Distributor / Retailer
    private String partyType;

    /// Party Details
    private String partyName;

    private String shopkeeperName;

    private String contactNumber;

    /// Date
    private LocalDate partyDate;

    /// Address
    private String shopAddress;

    /// Image
    private MultipartFile shopImage;

    /// Order Details
    private String orderType;

    private Integer quantity;

    private String remarks;

    private String flavour;

    /// GST
    private String gstin;

    private LocalDate orderDate;

    private LocalDate deliveryDate;

}
