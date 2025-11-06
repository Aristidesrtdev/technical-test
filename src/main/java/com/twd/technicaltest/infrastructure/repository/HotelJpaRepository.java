package com.twd.technicaltest.infrastructure.repository;

import com.twd.technicaltest.infrastructure.entity.HotelEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface HotelJpaRepository extends JpaRepository<HotelEntity,Integer> {

    Page<HotelEntity> findByAddress_CityContainingIgnoreCase(String city, Pageable pageable);
}
