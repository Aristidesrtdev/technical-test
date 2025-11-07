package com.twd.technicaltest.presentation.controller;

import com.twd.technicaltest.application.usecase.CreateHotelUseCase;
import com.twd.technicaltest.application.usecase.DeleteHotelUseCase;
import com.twd.technicaltest.application.usecase.GetAllHotelsUseCase;
import com.twd.technicaltest.application.usecase.UpdateHotelAddressUseCase;
import com.twd.technicaltest.presentation.dto.request.AddressRequestDTO;
import com.twd.technicaltest.presentation.dto.request.HotelRequestDTO;
import com.twd.technicaltest.presentation.dto.response.HotelResponseDTO;
import com.twd.technicaltest.presentation.dto.response.PagedResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@RestController
@RequestMapping("/api/hotel")
@RequiredArgsConstructor
public class HotelController {

    private final CreateHotelUseCase createHotelUseCase;
    private final GetAllHotelsUseCase getAllHotelsUseCase;
    private final UpdateHotelAddressUseCase updateHotelAddressUseCase;
    private final DeleteHotelUseCase deleteHotelUseCase;

    @GetMapping
    public ResponseEntity<PagedResponseDTO<HotelResponseDTO>> getAllHotel(@RequestParam Optional<String> city, Pageable pageable) {

        Page<HotelResponseDTO> response = getAllHotelsUseCase.getAllHotels(city, pageable);

        PagedResponseDTO<HotelResponseDTO> responsePaged = new PagedResponseDTO<>(
                response.getContent(),
                response.getNumber(),
                response.getSize(),
                response.getTotalElements(),
                response.getTotalPages(),
                response.isLast()
        );
        return new ResponseEntity<>(responsePaged, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HotelResponseDTO> createHotel(@Valid @RequestBody HotelRequestDTO hotelRequestDTO) {

        HotelResponseDTO response = createHotelUseCase.create(hotelRequestDTO);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/address")
    public ResponseEntity<HotelResponseDTO> updateAddress(
            @PathVariable Long id,
            @Valid @RequestBody AddressRequestDTO addressRequestDTO) {

        HotelResponseDTO response = updateHotelAddressUseCase.updateAddress(id, addressRequestDTO);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable Long id) {

        deleteHotelUseCase.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
