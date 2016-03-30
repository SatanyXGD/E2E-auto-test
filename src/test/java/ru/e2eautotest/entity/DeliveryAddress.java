package ru.e2eautotest.entity;

public class DeliveryAddress {
    private String city;
    private String street;
    private String house;
    private String apartment;

    public DeliveryAddress(String city, String street, String house, String apartment) {
        this.city = city;
        this.street = street;
        this.house = house;
        this.apartment = apartment;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getHouse() {
        return house;
    }

    public String getApartment() {
        return apartment;
    }
}
