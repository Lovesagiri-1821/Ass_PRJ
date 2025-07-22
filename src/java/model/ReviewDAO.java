// src/main/java/com/yourpackage/dao/ReviewDAO.java
package model;

import utils.DbUtils;
import model.ReviewDAO;
import model.ReviewDTO;

import java.sql.Connection; // Import standard Java SQL Connection
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp; // Needed for converting to LocalDateTime
import java.time.LocalDateTime; // Already imported, just for clarity
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {

    public int addReviewDTO(ReviewDTO reviewDTO) {
        String SQL = "INSERT INTO Review (user_id, property_id, rating, comment) VALUES (?, ?, ?, ?)";
        int generatedId = -1;
        Connection conn = null; // Declare connection outside try-with-resources for finally block
        try {
            conn = DbUtils.getConnection(); // Use your DBConnection utility
            try (PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setInt(1, reviewDTO.getUserID());
                pstmt.setInt(2, reviewDTO.getPropertyId());
                pstmt.setInt(3, reviewDTO.getRating());
                pstmt.setString(4, reviewDTO.getComment());
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    try (ResultSet rs = pstmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            generatedId = rs.getInt(1);
                            reviewDTO.setReviewId(generatedId);
                        }
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException ex) { // Catch both SQLException and ClassNotFoundException
            System.err.println("Lỗi khi thêm đánh giá: " + ex.getMessage());
        } finally {
            DbUtils.closeConnection(conn); // Ensure connection is closed
        }
        return generatedId;
    }

    public ReviewDTO getReviewDTOById(int reviewId) {
        String SQL = "SELECT review_id, user_id, property_id, rating, comment, created_at FROM Review WHERE review_id = ?";
        ReviewDTO reviewDTO = null;
        Connection conn = null;
        try {
            conn = DbUtils.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(SQL)) {
                pstmt.setInt(1, reviewId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        reviewDTO = new ReviewDTO(
                            rs.getInt("review_id"),
                            rs.getInt("user_id"),
                            rs.getInt("property_id"),
                            rs.getInt("rating"),
                            rs.getString("comment"),
                            rs.getTimestamp("created_at")
                        );
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println("Lỗi khi lấy đánh giá theo ID: " + ex.getMessage());
        } finally {
            DbUtils.closeConnection(conn);
        }
        return reviewDTO;
    }

    public List<ReviewDTO> getReviewsDTOByPropertyId(int propertyId) {
        String SQL = "SELECT review_id, user_id, property_id, rating, comment, created_at FROM Review WHERE property_id = ? ORDER BY created_at DESC";
        List<ReviewDTO> reviewDTOs = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DbUtils.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(SQL)) {
                pstmt.setInt(1, propertyId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        reviewDTOs.add(new ReviewDTO(
                            rs.getInt("review_id"),
                            rs.getInt("user_id"),
                            rs.getInt("property_id"),
                            rs.getInt("rating"),
                            rs.getString("comment"),
                            rs.getTimestamp("created_at")
                        ));
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println("Lỗi khi lấy đánh giá theo Property ID: " + ex.getMessage());
        } finally {
            DbUtils.closeConnection(conn);
        }
        return reviewDTOs;
    }

    public List<ReviewDTO> getReviewsDTOByUserId(int userId) {
        String SQL = "SELECT review_id, user_id, property_id, rating, comment, created_at FROM Review WHERE user_id = ? ORDER BY created_at DESC";
        List<ReviewDTO> reviewDTOs = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DbUtils.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(SQL)) {
                pstmt.setInt(1, userId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        reviewDTOs.add(new ReviewDTO(
                            rs.getInt("review_id"),
                            rs.getInt("user_id"),
                            rs.getInt("property_id"),
                            rs.getInt("rating"),
                            rs.getString("comment"),
                            rs.getTimestamp("created_at")
                        ));
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println("Lỗi khi lấy đánh giá theo User ID: " + ex.getMessage());
        } finally {
            DbUtils.closeConnection(conn);
        }
        return reviewDTOs;
    }

    public boolean updateReviewDTO(ReviewDTO reviewDTO) {
        String SQL = "UPDATE Review SET rating = ?, comment = ? WHERE review_id = ?";
        boolean updated = false;
        Connection conn = null;
        try {
            conn = DbUtils.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(SQL)) {
                pstmt.setInt(1, reviewDTO.getRating());
                pstmt.setString(2, reviewDTO.getComment());
                pstmt.setInt(3, reviewDTO.getReviewId());
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) { updated = true; }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println("Lỗi khi cập nhật đánh giá: " + ex.getMessage());
        } finally {
            DbUtils.closeConnection(conn);
        }
        return updated;
    }

    public boolean deleteReviewDTO(int reviewId) {
        String SQL = "DELETE FROM Review WHERE review_id = ?";
        boolean deleted = false;
        Connection conn = null;
        try {
            conn = DbUtils.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(SQL)) {
                pstmt.setInt(1, reviewId);
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) { deleted = true; }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println("Lỗi khi xóa đánh giá: " + ex.getMessage());
        } finally {
            DbUtils.closeConnection(conn);
        }
        return deleted;
    }


public boolean hasBookedProperty(int userId, int propertyId) {
    String sql = "SELECT COUNT(*) FROM Booking WHERE user_id = ? AND property_id = ?";
    try (Connection conn = DbUtils.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, userId);
        ps.setInt(2, propertyId);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
    } catch (SQLException | ClassNotFoundException e) {
        System.err.println("Error check hasBookedProperty: " + e.getMessage());
    }
    return false;
}



}