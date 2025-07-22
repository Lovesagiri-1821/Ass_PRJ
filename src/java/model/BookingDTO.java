package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BookingDTO {
    private int bookingId;
    private int userId;
    private int propertyId;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private int numberOfGuests;
    private double totalPrice;
    private String status;
    private LocalDateTime bookingDateTime;
    private double refundAmount;

    public BookingDTO() {
    }

    public BookingDTO(int bookingId, int userId, int propertyId, LocalDate checkIn, LocalDate checkOut,
                      int numberOfGuests, double totalPrice, String status, LocalDateTime bookingDateTime, double refundAmount) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.propertyId = propertyId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.numberOfGuests = numberOfGuests;
        this.totalPrice = totalPrice;
        this.status = status;
        this.bookingDateTime = bookingDateTime;
        this.refundAmount = refundAmount;
    }

    // Getter & Setter
    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getBookingDateTime() {
        return bookingDateTime;
    }

    public void setBookingDateTime(LocalDateTime bookingDateTime) {
        this.bookingDateTime = bookingDateTime;
    }

    public double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(double refundAmount) {
        this.refundAmount = refundAmount;
    }
}
