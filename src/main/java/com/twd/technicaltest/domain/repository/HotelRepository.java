package com.twd.technicaltest.domain.repository;

import com.twd.technicaltest.domain.model.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface HotelRepository {

    Hotel create(Hotel hotel);

    Page<Hotel> findAll(Optional<String> city, Pageable pageable);

    Hotel findById(Long id);

    Hotel updateAddress(Hotel existingHotel);

    void delete(Long id);

    boolean existById(Long id);
}
