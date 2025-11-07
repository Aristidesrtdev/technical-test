package com.twd.technicaltest.application.usecase;


import com.twd.technicaltest.application.mappers.AddressApplicationMapper;
import com.twd.technicaltest.application.mappers.HotelApplicationMapper;
import com.twd.technicaltest.domain.model.Address;
import com.twd.technicaltest.domain.model.Hotel;
import com.twd.technicaltest.domain.repository.HotelRepository;
import com.twd.technicaltest.presentation.dto.request.AddressRequestDTO;
import com.twd.technicaltest.presentation.dto.response.HotelResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateHotelAddressUseCaseImpl implements UpdateHotelAddressUseCase {

    private final HotelApplicationMapper hotelApplicationMapper;
    private final AddressApplicationMapper addressApplicationMapper;

    private final HotelRepository hotelRepository;


    @Override
    public HotelResponseDTO updateAddress(Long id, AddressRequestDTO addressRequestDTO) {

        Hotel existingHotel = hotelRepository.findById(id);

        Address newAddress = addressApplicationMapper.toModel(addressRequestDTO);

        existingHotel = existingHotel.updateAddress(newAddress);

        Hotel updatedHotel = hotelRepository.updateAddress(existingHotel);

        return hotelApplicationMapper.toResponseDTO(updatedHotel);
    }
}
