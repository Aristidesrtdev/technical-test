package com.twd.technicaltest.infrastructure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twd.technicaltest.application.usecase.*;
import com.twd.technicaltest.presentation.dto.request.AddressRequestDTO;
import com.twd.technicaltest.presentation.dto.request.HotelRequestDTO;
import com.twd.technicaltest.presentation.dto.response.AddressResponseDTO;
import com.twd.technicaltest.presentation.dto.response.HotelResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
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

    private HotelRequestDTO hotelRequestDTO;
    private HotelResponseDTO hotelResponseDTO;
    private AddressRequestDTO addressRequestDTO;

    @BeforeEach
    void setUp() {
        hotelRequestDTO = new HotelRequestDTO(
                "Hotel Gran Canaria",
                5,
                new AddressRequestDTO("Av. Canteras", "Las Palmas", "España", "35010")
        );

        hotelResponseDTO = new HotelResponseDTO(
                1L,
                "Hotel Gran Canaria",
                5,
                new AddressResponseDTO("Av. Canteras", "Las Palmas", "España", "35010")
        );

        addressRequestDTO = new AddressRequestDTO(
                "Nueva Calle", "Barcelona", "España", "08001"
        );
    }

    @Test
    @DisplayName("POST /api/hotel - Debe crear un hotel exitosamente con status 201")
    @WithMockUser
    void createHotel_WithValidData_ShouldReturnCreated() throws Exception {

        when(createHotelUseCase.create(any(HotelRequestDTO.class))).thenReturn(hotelResponseDTO);


        mockMvc.perform(post("/api/hotel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(hotelRequestDTO)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Hotel Gran Canaria")))
                .andExpect(jsonPath("$.stars", is(5)))
                .andExpect(jsonPath("$.address.street", is("Av. Canteras")))
                .andExpect(jsonPath("$.address.city", is("Las Palmas")));

        verify(createHotelUseCase, times(1)).create(any(HotelRequestDTO.class));
    }

    @Test
    @DisplayName("GET /api/hotel - Debe retornar lista paginada de hoteles")
    @WithMockUser
    void getAllHotels_WithPagination_ShouldReturnPagedResults() throws Exception {

        List<HotelResponseDTO> hotels = List.of(
                new HotelResponseDTO(1L, "Hotel A", 4,
                        new AddressResponseDTO("Calle A", "Madrid", "España", "28001")),
                new HotelResponseDTO(2L, "Hotel B", 5,
                        new AddressResponseDTO("Calle B", "Sevilla", "España", "41001"))
        );

        Page<HotelResponseDTO> page = new PageImpl<>(hotels, PageRequest.of(0, 10), 2);

        when(getAllHotelsUseCase.getAllHotels(any(Optional.class), any(Pageable.class)))
                .thenReturn(page);

        mockMvc.perform(get("/api/hotel")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].name", is("Hotel A")))
                .andExpect(jsonPath("$.content[1].name", is("Hotel B")))
                .andExpect(jsonPath("$.page", is(0)))
                .andExpect(jsonPath("$.size", is(10)))
                .andExpect(jsonPath("$.totalElements", is(2)))
                .andExpect(jsonPath("$.totalPages", is(1)))
                .andExpect(jsonPath("$.last", is(true)));

        verify(getAllHotelsUseCase, times(1)).getAllHotels(any(Optional.class), any(Pageable.class));
    }

    @Test
    @DisplayName("GET /api/hotel - Debe retornar lista vacía cuando no hay hoteles")
    @WithMockUser
    void getAllHotels_WhenNoHotels_ShouldReturnEmptyList() throws Exception {
        Page<HotelResponseDTO> emptyPage = new PageImpl<>(List.of());
        when(getAllHotelsUseCase.getAllHotels(eq(Optional.empty()), any(Pageable.class)))
                .thenReturn(emptyPage);

        mockMvc.perform(get("/api/hotel")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)))
                .andExpect(jsonPath("$.totalElements", is(0)));

        verify(getAllHotelsUseCase, times(1)).getAllHotels(eq(Optional.empty()), any(Pageable.class));
    }

    @Test
    @DisplayName("PUT /api/hotel/{id}/address - Debe actualizar dirección")
    @WithMockUser
    void updateHotelAddress_WithValidData_ShouldReturnUpdatedHotel() throws Exception {

        HotelResponseDTO updatedHotel = new HotelResponseDTO(
                1L, "Hotel A", 4,
                new AddressResponseDTO("Nueva Calle", "Barcelona", "España", "08001")
        );

        when(updateHotelAddressUseCase.updateAddress(eq(1L), any(AddressRequestDTO.class)))
                .thenReturn(updatedHotel);

        mockMvc.perform(put("/api/hotel/1/address")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addressRequestDTO)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.address.street", is("Nueva Calle")))
                .andExpect(jsonPath("$.address.city", is("Barcelona")));

        verify(updateHotelAddressUseCase, times(1)).updateAddress(eq(1L), any(AddressRequestDTO.class));
    }

    @Test
    @DisplayName("DELETE /api/hotel/{id} - Debe eliminar hotel")
    @WithMockUser(roles = "ADMIN")
    void deleteHotel_WithValidId_ShouldReturnNoContent() throws Exception {

        doNothing().when(deleteHotelUseCase).delete(1L);

        mockMvc.perform(delete("/api/hotel/1"))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(deleteHotelUseCase, times(1)).delete(1L);
    }

    @Test
    @DisplayName("GET /api/hotel - Debe filtrar por ciudad")
    @WithMockUser
    void getAllHotels_WithCityFilter_ShouldReturnFilteredResults() throws Exception {
        List<HotelResponseDTO> filteredHotels = List.of(
                new HotelResponseDTO(1L, "Hotel Madrid", 4,
                        new AddressResponseDTO("Calle Mayor", "Madrid", "España", "28001"))
        );

        Page<HotelResponseDTO> page = new PageImpl<>(filteredHotels);

        when(getAllHotelsUseCase.getAllHotels(eq(Optional.of("Madrid")), any(Pageable.class)))
                .thenReturn(page);

        mockMvc.perform(get("/api/hotel")
                        .param("city", "Madrid")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].address.city", is("Madrid")));

        verify(getAllHotelsUseCase, times(1)).getAllHotels(eq(Optional.of("Madrid")), any(Pageable.class));
    }
}