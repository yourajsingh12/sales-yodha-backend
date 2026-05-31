package com.salesyodha.salesyodha_backend.ServiceImpl.EmployeeServiceImpl;

import com.salesyodha.salesyodha_backend.Config.JwtService;
import com.salesyodha.salesyodha_backend.Dto.ApiResponse;
import com.salesyodha.salesyodha_backend.Dto.EmployeeDto.PartyOrderRequestDTO;
import com.salesyodha.salesyodha_backend.Dto.EmployeeDto.PartyOrderResponseDTO;
import com.salesyodha.salesyodha_backend.Entity.EmployeEntity.EmployeeEntity;
import com.salesyodha.salesyodha_backend.Entity.EmployeEntity.NewPartyEntity;
import com.salesyodha.salesyodha_backend.Entity.EmployeEntity.PartyOrderEntity;
import com.salesyodha.salesyodha_backend.Enum.OrderStatus;
import com.salesyodha.salesyodha_backend.Reposetory.EmployeeReposetory.EmployeeRepository;
import com.salesyodha.salesyodha_backend.Reposetory.EmployeeReposetory.NewPartyRepository;
import com.salesyodha.salesyodha_backend.Reposetory.EmployeeReposetory.PartyOrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PartyOrderServiceImpl {

    private final PartyOrderRepository partyOrderRepository;
    private final NewPartyRepository partyRepository;
    private final EmployeeRepository employeeRepository;
    private final JwtService jwtService;


    private PartyOrderResponseDTO mapToDTO(
            PartyOrderEntity order
    ) {

        return PartyOrderResponseDTO.builder()
                .id(order.getId())
                .partyId(order.getParty().getId())
                .partyName(order.getParty().getPartyName())
                .orderType(order.getOrderType())
                .quantity(order.getQuantity())
                .flavour(order.getFlavour())
                .remarks(order.getRemarks())
                .orderDate(order.getOrderDate())
                .deliveryDate(order.getDeliveryDate())
                .orderStatus(order.getOrderStatus().name())
                .createdAt(order.getCreatedAt())
                .build();
    }



    @Transactional
    public ApiResponse<?> saveOrder(
            PartyOrderRequestDTO dto,
            String token
    ) {

        EmployeeEntity employee = getEmployee(token);

        NewPartyEntity party = partyRepository.findById(dto.getPartyId())
                .orElseThrow(() ->
                        new RuntimeException("Party not found"));

        if (!party.getEmployee().getId()
                .equals(employee.getId())) {

            throw new RuntimeException(
                    "Unauthorized Access"
            );
        }

        PartyOrderEntity order =
                PartyOrderEntity.builder()
                        .party(party)
                        .orderType(dto.getOrderType())
                        .quantity(dto.getQuantity())
                        .flavour(dto.getFlavour())
                        .deliveryDate(dto.getDeliveryDate())
                        .remarks(dto.getRemarks())
                        .orderDate(dto.getOrderDate())
                        .build();

        partyOrderRepository.save(order);

        return ApiResponse.success(
                "Order Saved Successfully",
                mapToDTO(order)
        );
    }

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

    @Transactional()
    public ApiResponse<?> getPartyOrders(
            Long partyId,
            String token
    ) {

        EmployeeEntity employee = getEmployee(token);

        NewPartyEntity party = partyRepository.findById(partyId)
                .orElseThrow(() ->
                        new RuntimeException("Party not found"));

        if (!party.getEmployee().getId()
                .equals(employee.getId())) {

            throw new RuntimeException(
                    "Unauthorized Access"
            );
        }

        List<PartyOrderResponseDTO> response =
                partyOrderRepository.findByParty(party)
                        .stream()
                        .map(this::mapToDTO)
                        .toList();

        return ApiResponse.success(
                "Order List Fetched",
                response
        );
    }

    @Transactional
    public ApiResponse<?> updateOrderStatus(
            Long id,
            String status,
            String token
    ) {

        EmployeeEntity employee = getEmployee(token);

        PartyOrderEntity order =
                partyOrderRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Order not found"
                                ));

        if (!order.getParty()
                .getEmployee()
                .getId()
                .equals(employee.getId())) {

            throw new RuntimeException(
                    "Unauthorized Access"
            );
        }

        order.setOrderStatus(
                OrderStatus.valueOf(
                        status.toUpperCase()
                )
        );

        if (order.getOrderStatus()
                == OrderStatus.DELIVERED) {

            order.setDeliveryDate(
                    LocalDate.now()
            );
        }

        partyOrderRepository.save(order);

        return ApiResponse.success(
                "Order Status Updated",
                mapToDTO(order)
        );
    }
}
