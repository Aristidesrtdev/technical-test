package com.twd.technicaltest.application.usecase;

import com.twd.technicaltest.presentation.dto.response.HotelResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface GetAllHotelsUseCase {

    Page<HotelResponseDTO> getAllHotels(Optional<String> city, Pageable pageable);
}
