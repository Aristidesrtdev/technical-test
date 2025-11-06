package com.twd.technicaltest.application.mappers;

import com.twd.technicaltest.domain.model.Hotel;
import com.twd.technicaltest.presentation.dto.request.HotelRequestDTO;
import com.twd.technicaltest.presentation.dto.response.HotelResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HotelApplicationMapper {

    Hotel toModel(HotelRequestDTO hotelRequestDTO);

    HotelResponseDTO toResponseDTO(Hotel hotel);
}
