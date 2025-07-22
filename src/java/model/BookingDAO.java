package model;

import utils.DbUtils;
import java.sql.*;
import java.util.List;

public class BookingDAO {

    private static final String INSERT_BOOKING = "INSERT INTO Booking (user_id, property_id, check_in, check_out, number_of_guests,"
            + " total_price, status, booking_date_time, refund_amount) VALUES (?,?,?,?,?,?,?,?,?)";

    /**
     * Tạo mới booking, trả về ID đã sinh hoặc -1 nếu lỗi.
     */
    public int createBooking(BookingDTO dto) throws SQLException, ClassNotFoundException {
        int generatedId = -1;
        try (Connection con = DbUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_BOOKING, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, dto.getUserId());
            ps.setInt(2, dto.getPropertyId());
            ps.setDate(3, Date.valueOf(dto.getCheckIn()));
            ps.setDate(4, Date.valueOf(dto.getCheckOut()));
            ps.setInt(5, dto.getNumberOfGuests());
            ps.setDouble(6, dto.getTotalPrice());
            ps.setString(7, dto.getStatus());
            ps.setTimestamp(8, Timestamp.valueOf(dto.getBookingDateTime()));
            ps.setDouble(9, dto.getRefundAmount());

            if (ps.executeUpdate() > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedId = rs.getInt(1);
                    }
                }
            }
        }
        return generatedId;
    }

    /**
     * Kiểm tra xem user có từng đặt property hay chưa.
     */
    public boolean hasBookedProperty(int userId, int propertyId) {
        String sql = "SELECT COUNT(*) FROM Booking WHERE user_id = ? AND property_id = ?";
        try (Connection con = DbUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, propertyId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            System.err.println("Lỗi hasBookedProperty: " + e.getMessage());
            return false;
        }
    }

    public boolean cancelBooking(int bookingId) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Booking SET status='canceled' WHERE booking_id = ?";
        try (Connection con = DbUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, bookingId);
            return ps.executeUpdate() > 0;
        }
    }

    public BookingDTO getBookingById(int bookingId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Booking WHERE booking_id = ?";
        try (Connection con = DbUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, bookingId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    BookingDTO dto = new BookingDTO();
                    dto.setBookingId(rs.getInt("booking_id"));
                    dto.setUserId(rs.getInt("user_id"));
                    dto.setPropertyId(rs.getInt("property_id"));
                    dto.setCheckIn(rs.getDate("check_in").toLocalDate());
                    dto.setCheckOut(rs.getDate("check_out").toLocalDate());
                    dto.setNumberOfGuests(rs.getInt("number_of_guests"));
                    dto.setTotalPrice(rs.getDouble("total_price"));
                    dto.setStatus(rs.getString("status"));
                    dto.setBookingDateTime(rs.getTimestamp("booking_date_time").toLocalDateTime());
                    dto.setRefundAmount(rs.getDouble("refund_amount"));
                    return dto;
                }
            }
        }
        return null;
    }

    // Chưa triển khai
    public List<BookingDTO> getBookingsByUserId(int userID) {
        throw new UnsupportedOperationException("Chưa triển khai");
    }
}
