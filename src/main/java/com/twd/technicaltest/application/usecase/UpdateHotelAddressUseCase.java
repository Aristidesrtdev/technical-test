package com.twd.technicaltest.application.usecase;

import com.twd.technicaltest.presentation.dto.request.AddressRequestDTO;
import com.twd.technicaltest.presentation.dto.response.HotelResponseDTO;

public interface UpdateHotelAddressUseCase {

    HotelResponseDTO updateAddress(Long id, AddressRequestDTO addressRequestDTO);
}
