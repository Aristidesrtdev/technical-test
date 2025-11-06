package com.twd.technicaltest.application.usecase;

import com.twd.technicaltest.application.mappers.HotelApplicationMapper;
import com.twd.technicaltest.domain.model.Hotel;
import com.twd.technicaltest.domain.repository.HotelRepository;
import com.twd.technicaltest.presentation.dto.response.HotelResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class GetAllHotelsUseCaseImpl implements GetAllHotelsUseCase {

    private final HotelApplicationMapper hotelApplicationMapper;

    private final HotelRepository hotelRepository;

    @Override
    public Page<HotelResponseDTO> getAllHotels(Optional<String> city, Pageable pageable) {

        Page<Hotel> hotelPage = hotelRepository.findAll(city, pageable);

        return hotelPage.map(hotelApplicationMapper::toResponseDTO);
    }
}
