/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class PaymentDTO {
    private int paymentId;
    private int bookingId;
    private String method;              // qr_manual, momo, bank_transfer, etc
    private BigDecimal amount;
    private String status;              // pending, success, failed
    private Timestamp paymentDatetime;

    public PaymentDTO() {}

    public PaymentDTO(int bookingId, String method, BigDecimal amount, String status) {
        this.bookingId = bookingId;
        this.method = method;
        this.amount = amount;
        this.status = status;
        this.paymentDatetime = new Timestamp(System.currentTimeMillis());
    }

    // Getters & Setters

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getPaymentDatetime() {
        return paymentDatetime;
    }

    public void setPaymentDatetime(Timestamp paymentDatetime) {
        this.paymentDatetime = paymentDatetime;
    }
}

