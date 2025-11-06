package com.twd.technicaltest.domain.model;

public class Hotel {

    private final Long id;
    private final String name;
    private final Integer stars;
    private final Address address;

    public Hotel(Long id, String name, Integer stars, Address address) {
        this.id = id;
        this.name = name;
        this.stars = stars;
        this.address = address;
    }

    public Hotel updateAddress(Address address){
        return new Hotel(
                this.id,
                this.name,
                this.stars,
                address
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getStars() {
        return stars;
    }

    public Address getAddress() {
        return address;
    }
}
