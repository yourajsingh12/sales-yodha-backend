package com.salesyodha.salesyodha_backend.ServiceImpl.EmployeeServiceImpl;

import com.salesyodha.salesyodha_backend.Config.JwtService;
import com.salesyodha.salesyodha_backend.Dto.ApiResponse;
import com.salesyodha.salesyodha_backend.Dto.EmployeeDto.NewPartRequestPartyDto;
import com.salesyodha.salesyodha_backend.Dto.EmployeeDto.NewPartyResponseDTO;
import com.salesyodha.salesyodha_backend.Entity.EmployeEntity.EmployeeEntity;
import com.salesyodha.salesyodha_backend.Entity.EmployeEntity.NewPartyEntity;
import com.salesyodha.salesyodha_backend.Enum.OrderStatus;
import com.salesyodha.salesyodha_backend.Reposetory.EmployeeReposetory.EmployeeRepository;
import com.salesyodha.salesyodha_backend.Reposetory.EmployeeReposetory.NewPartyRepository;
import com.salesyodha.salesyodha_backend.Utility.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewPartyServiceImpl {

    private final NewPartyRepository newPartyRepository;
    private final EmployeeRepository employeeRepository;
    private final JwtService jwtService;
    private final FileUploadUtil fileUploadUtil;

    /// =========================================
    /// SAVE PARTY
    /// =========================================
    public ApiResponse<?> saveParty(
            NewPartRequestPartyDto dto,
            String token
    ) {

        EmployeeEntity employee = getEmployee(token);

        NewPartyEntity party = new NewPartyEntity();

        /// SET COMMON DATA
        setPartyData(party, dto);


        /// SAVE IMAGE
        if (dto.getShopImage() != null &&
                !dto.getShopImage().isEmpty()) {

            party.setShopImage(
                    fileUploadUtil.saveFile(
                            dto.getShopImage()
                    )
            );
        }

        /// SET EMPLOYEE
        party.setEmployee(employee);

        /// SAVE
        newPartyRepository.save(party);

        return ApiResponse.success(
                "Party Saved Successfully",
                mapToDTO(party)
        );
    }

    /// =========================================
    /// UPDATE PARTY
    /// =========================================
    public ApiResponse<?> updateParty(
            Long id,
            NewPartRequestPartyDto dto,
            String token
    ) {

        EmployeeEntity employee = getEmployee(token);

        NewPartyEntity party = getPartyById(id);

        /// OWNER VALIDATION
        validateOwner(employee, party);

        /// SET COMMON DATA
        setPartyData(party, dto);

        /// UPDATE IMAGE
        if (dto.getShopImage() != null &&
                !dto.getShopImage().isEmpty()) {

            party.setShopImage(
                    fileUploadUtil.saveFile(
                            dto.getShopImage()
                    )
            );
        }

        /// SAVE UPDATE
        newPartyRepository.save(party);

        return ApiResponse.success(
                "Party Updated Successfully",
                mapToDTO(party)
        );
    }

    /// =========================================
    /// GET ALL PARTY
    /// =========================================
    public ApiResponse<?> getAllParty(
            String token
    ) {

        EmployeeEntity employee = getEmployee(token);

        List<NewPartyEntity> partyList =
                newPartyRepository.findByEmployee(employee);

        List<NewPartyResponseDTO> response =
                partyList.stream()
                        .map(this::mapToDTO)
                        .toList();

        return ApiResponse.success(
                "Party List Fetched",
                response
        );
    }

    /// =========================================
    /// GET SINGLE PARTY
    /// =========================================
    public ApiResponse<?> getSingleParty(
            Long id,
            String token
    ) {

        EmployeeEntity employee = getEmployee(token);

        NewPartyEntity party = getPartyById(id);

        validateOwner(employee, party);

        return ApiResponse.success(
                "Party Details",
                mapToDTO(party)
        );
    }

    /// =========================================
    /// DELETE PARTY
    /// =========================================
    public ApiResponse<?> deleteParty(
            Long id,
            String token
    ) {

        EmployeeEntity employee = getEmployee(token);

        NewPartyEntity party = getPartyById(id);

        validateOwner(employee, party);

        newPartyRepository.delete(party);

        return ApiResponse.success(
                "Party Deleted Successfully",
                null
        );
    }

    /// =========================================
    /// COMMON PARTY DATA SETTER
    /// =========================================
    private void setPartyData(
            NewPartyEntity party,
            NewPartRequestPartyDto dto
    ) {

        party.setPartyType(dto.getPartyType());
        party.setPartyName(dto.getPartyName());
        party.setShopkeeperName(dto.getShopkeeperName());
        party.setContactNumber(dto.getContactNumber());
        party.setPartyDate(dto.getPartyDate());
        party.setShopAddress(dto.getShopAddress());
        party.setGstin(dto.getGstin());
    }

    /// =========================================
    /// DTO MAPPING
    /// =========================================
    private NewPartyResponseDTO mapToDTO(
            NewPartyEntity party
    ) {

        return NewPartyResponseDTO.builder()

                .id(party.getId())
                .partyType(party.getPartyType())
                .partyName(party.getPartyName())
                .shopkeeperName(party.getShopkeeperName())
                .contactNumber(party.getContactNumber())
                .partyDate(party.getPartyDate())
                .shopAddress(party.getShopAddress())
                .shopImage(party.getShopImage())
                .gstin(party.getGstin())
                .createdAt(party.getCreatedAt())

                .build();
    }

    /// =========================================
    /// GET PARTY BY ID
    /// =========================================
    private NewPartyEntity getPartyById(
            Long id
    ) {

        return newPartyRepository.findById(id)

                .orElseThrow(() ->
                        new RuntimeException(
                                "Party not found"
                        ));
    }

    /// =========================================
    /// OWNER VALIDATION
    /// =========================================
    private void validateOwner(
            EmployeeEntity employee,
            NewPartyEntity party
    ) {

        if (!party.getEmployee().getId()
                .equals(employee.getId())) {

            throw new RuntimeException(
                    "Unauthorized Access"
            );
        }
    }

    /// =========================================
    /// GET EMPLOYEE FROM TOKEN
    /// =========================================
    private EmployeeEntity getEmployee(
            String token
    ) {

        String phone =
                jwtService.extractPhoneNumber(token);

        return employeeRepository.findByMobileNumber(phone)

                .orElseThrow(() ->
                        new RuntimeException(
                                "Employee not found"
                        ));
    }




}