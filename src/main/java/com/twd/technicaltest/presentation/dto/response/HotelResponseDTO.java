package com.twd.technicaltest.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelResponseDTO{

    private Long id;
    private String name;
    private Integer stars;
    private AddressResponseDTO address;

}
