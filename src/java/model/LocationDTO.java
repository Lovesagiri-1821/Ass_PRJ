// src/main/java/com/yourpackage/dto/LocationDTO.java
package com.yourpackage.dto;

public class LocationDTO {
    private int locationId;
    private String city;

    public LocationDTO() {}

    public LocationDTO(int locationId, String city) {
        this.locationId = locationId;
        this.city = city;
    }

    public LocationDTO(String city) {
        this.city = city;
    }

    // Getters and Setters
    public int getLocationId() { return locationId; }
    public void setLocationId(int locationId) { this.locationId = locationId; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    @Override
    public String toString() {
        return "LocationDTO{" + "locationId=" + locationId + ", city='" + city + '\'' + '}';
    }
}