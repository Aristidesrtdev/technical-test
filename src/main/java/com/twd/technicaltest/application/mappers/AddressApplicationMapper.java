package com.twd.technicaltest.application.mappers;

import com.twd.technicaltest.domain.model.Address;
import com.twd.technicaltest.presentation.dto.request.AddressRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressApplicationMapper {

    Address toModel(AddressRequestDTO addressRequestDTO);
}
