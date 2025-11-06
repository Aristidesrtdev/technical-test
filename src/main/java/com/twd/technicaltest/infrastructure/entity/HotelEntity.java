package com.twd.technicaltest.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hotel", uniqueConstraints = { @UniqueConstraint
        (name = "unique_hotel_name_address", columnNames = { "name", "city"})
})
public class HotelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "stars", nullable = false)
    private Integer stars;

    @Embedded
    @Column(name = "address", nullable = false)
    private AddressEmbeddable address;

}

