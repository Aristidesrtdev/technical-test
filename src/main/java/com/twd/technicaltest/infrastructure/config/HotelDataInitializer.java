package com.twd.technicaltest.infrastructure.config;

import com.twd.technicaltest.domain.model.Address;
import com.twd.technicaltest.domain.model.Hotel;
import com.twd.technicaltest.domain.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HotelDataInitializer implements CommandLineRunner {

    private final HotelRepository hotelRepository;

    @Override
    public void run(String... args) {

            String spain = "Spain";
            hotelRepository.create(new Hotel(null, "Hotel Gran Canaria", 5,
                    new Address("Av. de Las Canteras 123", "Las Palmas", spain, "35010")));

            hotelRepository.create(new Hotel(null, "Lopesan Costa Meloneras Resort & Spa", 5,
                    new Address("Calle Mar Mediterraneo 1", "Maspalomas", spain, "35100")));

            hotelRepository.create(new Hotel(null, "Riu Plaza España", 3,
                    new Address("Gran Via  84", "Madrid", spain, "28013")));

            hotelRepository.create(new Hotel(null, "Hotel del Sol", 5,
                    new Address("Av. del Sol 99", "Málaga", spain, "29015")));

            hotelRepository.create(new Hotel(null, "Hotel Atlántico", 4,
                    new Address("Rua das Flores 22", "Oporto", "Portugal", "4000-123")));

    }
}
