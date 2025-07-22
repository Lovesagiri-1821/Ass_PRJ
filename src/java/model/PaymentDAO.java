/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import utils.DbUtils;

import java.sql.*;

public class PaymentDAO {

    private static final String INSERT_PAYMENT =
        "INSERT INTO Payment (booking_id, method, amount, payment_status, payment_datetime) " +
        "VALUES (?, ?, ?, ?, ?)";

    /**
     * Thêm bản ghi thanh toán mới, trả về true nếu thành công
     */
    public boolean createPayment(PaymentDTO dto) throws SQLException, ClassNotFoundException {
        try (Connection con = DbUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_PAYMENT)) {

            ps.setInt(1, dto.getBookingId());
            ps.setString(2, dto.getMethod());
            ps.setBigDecimal(3, dto.getAmount());
            ps.setString(4, dto.getStatus());
            ps.setTimestamp(5, dto.getPaymentDatetime());

            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Cập nhật trạng thái thanh toán
     */
    public boolean updatePaymentStatus(int bookingId, String newStatus) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Payment SET payment_status = ? WHERE booking_id = ?";
        try (Connection con = DbUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newStatus);
            ps.setInt(2, bookingId);
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Kiểm tra xem booking đã có thanh toán thành công chưa
     */
    public boolean isPaid(int bookingId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM Payment WHERE booking_id = ? AND payment_status = 'success'";
        try (Connection con = DbUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, bookingId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }
}

