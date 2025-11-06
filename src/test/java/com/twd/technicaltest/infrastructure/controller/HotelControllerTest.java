package com.twd.technicaltest.infrastructure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twd.technicaltest.application.usecase.*;
import com.twd.technicaltest.infrastructure.security.JwtAuthenticationFilter;
import com.twd.technicaltest.infrastructure.security.JwtTokenProvider;
import com.twd.technicaltest.presentation.controller.HotelController;
import com.twd.technicaltest.presentation.dto.request.AddressRequestDTO;
import com.twd.technicaltest.presentation.dto.request.HotelRequestDTO;
import com.twd.technicaltest.presentation.dto.response.AddressResponseDTO;
import com.twd.technicaltest.presentation.dto.response.HotelResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = HotelController.class)
@AutoConfigureMockMvc(addFilters = false)
class HotelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CreateHotelUseCase createHotelUseCase;

    @MockitoBean
    private GetAllHotelsUseCase getAllHotelsUseCase;

    @MockitoBean
    private UpdateHotelAddressUseCase updateHotelAddressUseCase;

    @MockitoBean
    private DeleteHotelUseCase deleteHotelUseCase;

    @MockitoBean
    private JwtTokenProvider jwtTokenProvider;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    @DisplayName("POST /api/hotel - debe crear un hotel correctamente")
    void shouldCreateHotel() throws Exception {
        HotelRequestDTO hotel = new HotelRequestDTO("Hotel Gran Canaria", 5,
                new AddressRequestDTO("Av. Canteras", "Las Palmas", "España", "35010"));

        HotelResponseDTO response = new HotelResponseDTO(
                1L,
                "Hotel Gran Canaria",
                5,
                new AddressResponseDTO("Av. Canteras", "Las Palmas", "España", "35010")
        );

        Mockito.when(createHotelUseCase.create(any())).thenReturn(response);

        mockMvc.perform(post("/api/hotel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(hotel)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Hotel Gran Canaria"))
                .andExpect(jsonPath("$.stars").value(5));
    }

    @Test
    @DisplayName("GET /api/hotel - debe listar hoteles paginados correctamente")
    void shouldGetAllHotelsPaged() throws Exception {

        List<HotelResponseDTO> hotels = List.of(
                new HotelResponseDTO(1L, "Hotel A", 4,
                        new AddressResponseDTO("Calle A", "Madrid", "España", "28001")),
                new HotelResponseDTO(2L, "Hotel B", 5,
                        new AddressResponseDTO("Calle B", "Sevilla", "España", "41001"))
        );

        Page<HotelResponseDTO> page = new PageImpl<>(hotels);

        Mockito.when(getAllHotelsUseCase.getAllHotels(nullable(Optional.class), any(Pageable.class)))
                .thenReturn(page);

        mockMvc.perform(get("/api/hotel")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Hotel A"))
                .andExpect(jsonPath("$.content[1].name").value("Hotel B"));
    }

    @Test
    @DisplayName("PUT /api/hotel/{id}/address - debe actualizar dirección")
    void shouldUpdateHotelAddress() throws Exception {
        AddressRequestDTO newAddress = new AddressRequestDTO("Nueva Calle", "Barcelona", "España", "08001");

        HotelResponseDTO updatedHotel = new HotelResponseDTO(1L, "Hotel A", 4,
                new AddressResponseDTO("Nueva Calle", "Barcelona", "España", "08001"));

        Mockito.when(updateHotelAddressUseCase.updateAddress(eq(1), any())).thenReturn(updatedHotel);

        mockMvc.perform(put("/api/hotel/1/address")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newAddress)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.address.city").value("Barcelona"));
    }

    @Test
    @DisplayName("DELETE /api/hotel/{id} - debe eliminar un hotel")
    void shouldDeleteHotel() throws Exception {
        mockMvc.perform(delete("/api/hotel/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(deleteHotelUseCase).delete(1);
    }
}
