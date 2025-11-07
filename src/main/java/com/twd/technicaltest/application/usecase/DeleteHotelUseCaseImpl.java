package com.twd.technicaltest.application.usecase;

import com.twd.technicaltest.domain.repository.HotelRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteHotelUseCaseImpl implements DeleteHotelUseCase {

    private final HotelRepository hotelRepository;


    @Override
    public void delete(Long id) {
        if(!hotelRepository.existById(id)){
            throw new EntityNotFoundException("Hotel with id " + id + " not found");
        }
        hotelRepository.delete(id);
    }
}
