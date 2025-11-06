package com.twd.technicaltest.infrastructure.mappers;

import com.twd.technicaltest.domain.model.Hotel;
import com.twd.technicaltest.infrastructure.entity.HotelEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HotelPersistenceMapper {

    HotelEntity toEntity(Hotel hotel);

    Hotel toModel(HotelEntity hotelEntity);

}
