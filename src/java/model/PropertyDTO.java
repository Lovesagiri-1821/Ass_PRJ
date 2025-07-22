/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 *
 * @author Admin
 */
public class PropertyDTO {
    private int propertyId;
    private String name;
    private String description;
    private BigDecimal pricePerNight;

    // Location & Host
    private int locationId;
    private int hostId;

    // Details
    private int numBedrooms;
    private int numBathrooms;
    private int numGuests;
    private int numRooms;

    // Amenities
    private boolean hasWifi;
    private boolean allowsPets;
    private boolean hasBalcony;
    private boolean hasParking;
    private boolean hasPrivatePool;
    private boolean hasEvStation;
    private boolean allowsSmoking;

    // Relative Location
    private boolean nearBeach;
    private boolean nearLake;
    private boolean nearRiver;
    private boolean nearCountryside;
    private boolean nearCityCenter;

    // Distances
    private BigDecimal distanceToBeach;
    private BigDecimal distanceToLake;
    private BigDecimal distanceToCityCenter;

    // Extra Fields
    private String status;
    private Timestamp createdAt;
    private String thumbnailUrl;
    private String shortDescription;

    public PropertyDTO() {
    }

    public PropertyDTO(int propertyId, String name, String description, BigDecimal pricePerNight, int locationId, int hostId, int numBedrooms, int numBathrooms, int numGuests, int numRooms, boolean hasWifi, boolean allowsPets, boolean hasBalcony, boolean hasParking, boolean hasPrivatePool, boolean hasEvStation, boolean allowsSmoking, boolean nearBeach, boolean nearLake, boolean nearRiver, boolean nearCountryside, boolean nearCityCenter, BigDecimal distanceToBeach, BigDecimal distanceToLake, BigDecimal distanceToCityCenter, String status, Timestamp createdAt, String thumbnailUrl, String shortDescription) {
        this.propertyId = propertyId;
        this.name = name;
        this.description = description;
        this.pricePerNight = pricePerNight;
        this.locationId = locationId;
        this.hostId = hostId;
        this.numBedrooms = numBedrooms;
        this.numBathrooms = numBathrooms;
        this.numGuests = numGuests;
        this.numRooms = numRooms;
        this.hasWifi = hasWifi;
        this.allowsPets = allowsPets;
        this.hasBalcony = hasBalcony;
        this.hasParking = hasParking;
        this.hasPrivatePool = hasPrivatePool;
        this.hasEvStation = hasEvStation;
        this.allowsSmoking = allowsSmoking;
        this.nearBeach = nearBeach;
        this.nearLake = nearLake;
        this.nearRiver = nearRiver;
        this.nearCountryside = nearCountryside;
        this.nearCityCenter = nearCityCenter;
        this.distanceToBeach = distanceToBeach;
        this.distanceToLake = distanceToLake;
        this.distanceToCityCenter = distanceToCityCenter;
        this.status = status;
        this.createdAt = createdAt;
        this.thumbnailUrl = thumbnailUrl;
        this.shortDescription = shortDescription;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(BigDecimal pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getHostId() {
        return hostId;
    }

    public void setHostId(int hostId) {
        this.hostId = hostId;
    }

    public int getNumBedrooms() {
        return numBedrooms;
    }

    public void setNumBedrooms(int numBedrooms) {
        this.numBedrooms = numBedrooms;
    }

    public int getNumBathrooms() {
        return numBathrooms;
    }

    public void setNumBathrooms(int numBathrooms) {
        this.numBathrooms = numBathrooms;
    }

    public int getNumGuests() {
        return numGuests;
    }

    public void setNumGuests(int numGuests) {
        this.numGuests = numGuests;
    }

    public int getNumRooms() {
        return numRooms;
    }

    public void setNumRooms(int numRooms) {
        this.numRooms = numRooms;
    }

    public boolean isHasWifi() {
        return hasWifi;
    }

    public void setHasWifi(boolean hasWifi) {
        this.hasWifi = hasWifi;
    }

    public boolean isAllowsPets() {
        return allowsPets;
    }

    public void setAllowsPets(boolean allowsPets) {
        this.allowsPets = allowsPets;
    }

    public boolean isHasBalcony() {
        return hasBalcony;
    }

    public void setHasBalcony(boolean hasBalcony) {
        this.hasBalcony = hasBalcony;
    }

    public boolean isHasParking() {
        return hasParking;
    }

    public void setHasParking(boolean hasParking) {
        this.hasParking = hasParking;
    }

    public boolean isHasPrivatePool() {
        return hasPrivatePool;
    }

    public void setHasPrivatePool(boolean hasPrivatePool) {
        this.hasPrivatePool = hasPrivatePool;
    }

    public boolean isHasEvStation() {
        return hasEvStation;
    }

    public void setHasEvStation(boolean hasEvStation) {
        this.hasEvStation = hasEvStation;
    }

    public boolean isAllowsSmoking() {
        return allowsSmoking;
    }

    public void setAllowsSmoking(boolean allowsSmoking) {
        this.allowsSmoking = allowsSmoking;
    }

    public boolean isNearBeach() {
        return nearBeach;
    }

    public void setNearBeach(boolean nearBeach) {
        this.nearBeach = nearBeach;
    }

    public boolean isNearLake() {
        return nearLake;
    }

    public void setNearLake(boolean nearLake) {
        this.nearLake = nearLake;
    }

    public boolean isNearRiver() {
        return nearRiver;
    }

    public void setNearRiver(boolean nearRiver) {
        this.nearRiver = nearRiver;
    }

    public boolean isNearCountryside() {
        return nearCountryside;
    }

    public void setNearCountryside(boolean nearCountryside) {
        this.nearCountryside = nearCountryside;
    }

    public boolean isNearCityCenter() {
        return nearCityCenter;
    }

    public void setNearCityCenter(boolean nearCityCenter) {
        this.nearCityCenter = nearCityCenter;
    }

    public BigDecimal getDistanceToBeach() {
        return distanceToBeach;
    }

    public void setDistanceToBeach(BigDecimal distanceToBeach) {
        this.distanceToBeach = distanceToBeach;
    }

    public BigDecimal getDistanceToLake() {
        return distanceToLake;
    }

    public void setDistanceToLake(BigDecimal distanceToLake) {
        this.distanceToLake = distanceToLake;
    }

    public BigDecimal getDistanceToCityCenter() {
        return distanceToCityCenter;
    }

    public void setDistanceToCityCenter(BigDecimal distanceToCityCenter) {
        this.distanceToCityCenter = distanceToCityCenter;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

     
    
}
