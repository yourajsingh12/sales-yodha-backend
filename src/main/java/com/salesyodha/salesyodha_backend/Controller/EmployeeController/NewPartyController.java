package com.salesyodha.salesyodha_backend.Controller.EmployeeController;

import com.salesyodha.salesyodha_backend.Dto.ApiResponse;
import com.salesyodha.salesyodha_backend.Dto.EmployeeDto.NewPartRequestPartyDto;
import com.salesyodha.salesyodha_backend.ServiceImpl.EmployeeServiceImpl.NewPartyServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/party")
public class NewPartyController {

    private final NewPartyServiceImpl newPartyService;

    /// =========================================
    /// SAVE PARTY
    /// =========================================
    @PostMapping(
            value = "/save",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ApiResponse<?> saveParty(

            @ModelAttribute
            NewPartRequestPartyDto dto,

            @RequestHeader("Authorization")
            String token

    ) {

        return newPartyService.saveParty(
                dto,
                extractToken(token)
        );
    }

    /// =========================================
    /// UPDATE PARTY
    /// =========================================
    @PutMapping(
            value = "/update/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ApiResponse<?> updateParty(

            @PathVariable Long id,

            @ModelAttribute
            NewPartRequestPartyDto dto,

            @RequestHeader("Authorization")
            String token

    ) {

        return newPartyService.updateParty(
                id,
                dto,
                extractToken(token)
        );
    }

    /// =========================================
    /// GET ALL PARTY
    /// =========================================
    @GetMapping("/all")
    public ApiResponse<?> getAllParty(

            @RequestHeader("Authorization")
            String token

    ) {

        return newPartyService.getAllParty(
                extractToken(token)
        );
    }

    /// =========================================
    /// GET SINGLE PARTY
    /// =========================================
    @GetMapping("/{id}")
    public ApiResponse<?> getSingleParty(

            @PathVariable Long id,

            @RequestHeader("Authorization")
            String token

    ) {

        return newPartyService.getSingleParty(
                id,
                extractToken(token)
        );
    }

    /// =========================================
    /// DELETE PARTY
    /// =========================================
    @DeleteMapping("/delete/{id}")
    public ApiResponse<?> deleteParty(

            @PathVariable Long id,

            @RequestHeader("Authorization")
            String token

    ) {

        return newPartyService.deleteParty(
                id,
                extractToken(token)
        );
    }

    /// =========================================
    /// COMMON TOKEN EXTRACTOR
    /// =========================================
    private String extractToken(
            String token
    ) {

        return token.replace(
                "Bearer ",
                ""
        );
    }
}