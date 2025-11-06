package com.twd.technicaltest.presentation.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelRequestDTO{

    @NotBlank
    private String name;

    @Min(1)
    @Max(5)
    private Integer stars;

    @Valid
    private AddressRequestDTO address;
}