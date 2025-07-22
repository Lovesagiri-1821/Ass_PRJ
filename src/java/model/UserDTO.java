/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model; // Ensure your package name is correct, e.g., 'com.yourpackage.dto' if following previous structure

import java.io.Serializable;
import java.time.LocalDateTime; // Import for LocalDateTime

/**
 *
 * @author Admin
 */
public class UserDTO implements Serializable {

    private int userID;
    private String name;
    private String email;
    private String passwordHash; // Renamed for Java convention
    private String role;
    private boolean isDeleted; // New field for is_deleted
    private LocalDateTime createdAt; // Changed from Date to LocalDateTime

    public UserDTO() {
    }

    // Full constructor matching all table columns
    public UserDTO(int userID, String name, String email, String passwordHash, String role, boolean isDeleted, LocalDateTime createdAt) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
    }

    // Constructor for creating a new user (without auto-generated ID and default values)
    public UserDTO(String name, String email, String passwordHash, String role) {
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.isDeleted = false; // Default value from table
        this.createdAt = null; // Will be set by the database
    }

    // Getters and Setters
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() { // Getter for passwordHash
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) { // Setter for passwordHash
        this.passwordHash = passwordHash;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isDeleted() { // Getter for boolean is_deleted
        return isDeleted;
    }

    public void setDeleted(boolean deleted) { // Setter for boolean is_deleted
        isDeleted = deleted;
    }

    public LocalDateTime getCreatedAt() { // Getter for LocalDateTime
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) { // Setter for LocalDateTime
        this.createdAt = createdAt;
    }

    public int getUserId() {
        return userID;
    }

}
