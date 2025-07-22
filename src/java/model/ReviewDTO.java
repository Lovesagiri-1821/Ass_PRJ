// src/main/java/com/yourpackage/dto/ReviewDTO.java
package model;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ReviewDTO {

    private int reviewId;
    private int userID;
    private int propertyId;
    private int rating;
    private String comment;
    private Timestamp createdAt;

    public ReviewDTO() {
    }

    public ReviewDTO(int reviewId, int userID, int propertyId, int rating, String comment, Timestamp createdAt) {
        this.reviewId = reviewId;
        this.userID = userID;
        this.propertyId = propertyId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    

    
}
