package com.twd.technicaltest.presentation.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponseDTO {

    private String street;
    private String city;
    private String country;
    private String postalCode;

}

