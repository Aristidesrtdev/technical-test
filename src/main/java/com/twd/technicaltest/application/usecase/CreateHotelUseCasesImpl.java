package com.twd.technicaltest.application.usecase;

import com.twd.technicaltest.application.mappers.HotelApplicationMapper;
import com.twd.technicaltest.domain.model.Hotel;
import com.twd.technicaltest.domain.repository.HotelRepository;
import com.twd.technicaltest.presentation.dto.request.HotelRequestDTO;
import com.twd.technicaltest.presentation.dto.response.HotelResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateHotelUseCasesImpl implements CreateHotelUseCase{

    private final HotelApplicationMapper hotelApplicationMapper;

    private final HotelRepository hotelRepository;

    @Override
    public HotelResponseDTO create(HotelRequestDTO hotelRequestDTO) {

        Hotel hotel = hotelApplicationMapper.toModel(hotelRequestDTO);

        hotel = hotelRepository.create(hotel);

        return hotelApplicationMapper.toResponseDTO(hotel);
    }

}
