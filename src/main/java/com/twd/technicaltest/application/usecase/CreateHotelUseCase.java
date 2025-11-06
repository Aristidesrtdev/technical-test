package com.twd.technicaltest.application.usecase;

import com.twd.technicaltest.presentation.dto.request.HotelRequestDTO;
import com.twd.technicaltest.presentation.dto.response.HotelResponseDTO;

public interface CreateHotelUseCase {

    HotelResponseDTO create(HotelRequestDTO hotelRequestDTO);

}
