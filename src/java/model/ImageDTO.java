// src/main/java/com/yourpackage/dto/ImageDTO.java
package model;

public class ImageDTO {
    private int imageId;
    private int propertyId;
    private String imageUrl;
    private int sortOrder;

    public ImageDTO() {}

    public ImageDTO(int imageId, int propertyId, String imageUrl, int sortOrder) {
        this.imageId = imageId;
        this.propertyId = propertyId;
        this.imageUrl = imageUrl;
        this.sortOrder = sortOrder;
    }

    public ImageDTO(int propertyId, String imageUrl, int sortOrder) {
        this.propertyId = propertyId;
        this.imageUrl = imageUrl;
        this.sortOrder = sortOrder;
    }

    // Getters and Setters
    public int getImageId() { return imageId; }
    public void setImageId(int imageId) { this.imageId = imageId; }
    public int getPropertyId() { return propertyId; }
    public void setPropertyId(int propertyId) { this.propertyId = propertyId; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public int getSortOrder() { return sortOrder; }
    public void setSortOrder(int sortOrder) { this.sortOrder = sortOrder; }

    @Override
    public String toString() {
        return "ImageDTO{" + "imageId=" + imageId + ", propertyId=" + propertyId + ", imageUrl='" + imageUrl + '\'' + ", sortOrder=" + sortOrder + '}';
    }
}