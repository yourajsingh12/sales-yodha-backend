package com.salesyodha.salesyodha_backend.Controller.EmployeeController;

import com.salesyodha.salesyodha_backend.Dto.ApiResponse;
import com.salesyodha.salesyodha_backend.Dto.EmployeeDto.PartyOrderRequestDTO;
import com.salesyodha.salesyodha_backend.ServiceImpl.EmployeeServiceImpl.PartyOrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/party-order")
public class PartyOrderController {

    private final PartyOrderServiceImpl orderService;

    @PostMapping("/save")
    public ApiResponse<?> saveOrder(
            @RequestBody PartyOrderRequestDTO dto,
            @RequestHeader("Authorization") String token
    ) {
        return orderService.saveOrder(
                dto,
                token.replace("Bearer ", "")
        );
    }

    @GetMapping("/party/{partyId}")
    public ApiResponse<?> getPartyOrders(
            @PathVariable Long partyId,
            @RequestHeader("Authorization") String token
    ) {
        return orderService.getPartyOrders(
                partyId,
                token.replace("Bearer ", "")
        );
    }

    @PatchMapping("/status/{id}")
    public ApiResponse<?> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam String status,
            @RequestHeader("Authorization") String token
    ) {
        return orderService.updateOrderStatus(
                id,
                status,
                token.replace("Bearer ", "")
        );
    }
}
