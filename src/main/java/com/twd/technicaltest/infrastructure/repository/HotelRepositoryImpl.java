package com.twd.technicaltest.infrastructure.repository;

import com.twd.technicaltest.domain.model.Hotel;
import com.twd.technicaltest.domain.repository.HotelRepository;
import com.twd.technicaltest.infrastructure.entity.HotelEntity;
import com.twd.technicaltest.infrastructure.mappers.HotelPersistenceMapper;
import jakarta.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class HotelRepositoryImpl implements HotelRepository {

    private final HotelJpaRepository jpaRepository;
    private final HotelPersistenceMapper hotelPersistenceMapper;


    @Override
    public Hotel create(Hotel hotel) {

        HotelEntity entity = hotelPersistenceMapper.toEntity(hotel);

        HotelEntity saved = jpaRepository.save(entity);

        return hotelPersistenceMapper.toModel(saved);
    }

    @Override
    public Page<Hotel> findAll(Optional<String> city, Pageable pageable) {

        Page<HotelEntity> hotelEntityPage = city.filter(StringUtils::hasText)
                .map(c -> jpaRepository.findByAddress_CityContainingIgnoreCase(c, pageable))
                .orElseGet(() -> jpaRepository.findAll(pageable));

        return hotelEntityPage.map(hotelPersistenceMapper::toModel);
    }

    @Override
    public Hotel findById(Integer id) {

        HotelEntity hotelEntity = jpaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found Hotel with id: " + id));

        return hotelPersistenceMapper.toModel(hotelEntity);
    }

    @Override
    public Hotel updateAddress(Hotel existingHotel) {

        HotelEntity hotelEntity = hotelPersistenceMapper.toEntity(existingHotel);

        hotelEntity =  jpaRepository.save(hotelEntity);

        return hotelPersistenceMapper.toModel(hotelEntity);
    }

    @Override
    public void delete(Integer id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existById(Integer id) {
        return jpaRepository.existsById(id);
    }
}
