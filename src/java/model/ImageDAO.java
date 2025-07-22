// src/main/java/com/yourpackage/dao/ImageDAO.java
package model;

import utils.DbUtils; // Correctly import your DbUtils class

import java.sql.Connection; // Import standard Java SQL Connection
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ImageDAO {

    public int addImageDTO(ImageDTO imageDTO) {
        String SQL = "INSERT INTO Image (property_id, image_url, sort_order) VALUES (?, ?, ?)";
        int generatedId = -1;
        Connection conn = null; // Declare connection outside try-with-resources for finally block
        try {
            conn = DbUtils.getConnection(); // Use DbUtils to get connection
            try (PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setInt(1, imageDTO.getPropertyId());
                pstmt.setString(2, imageDTO.getImageUrl());
                pstmt.setInt(3, imageDTO.getSortOrder());
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    try (ResultSet rs = pstmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            generatedId = rs.getInt(1);
                            imageDTO.setImageId(generatedId);
                        }
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException ex) { // Catch both SQLException and ClassNotFoundException
            System.err.println("Lỗi khi thêm hình ảnh: " + ex.getMessage());
        } finally {
            DbUtils.closeConnection(conn); // Ensure connection is closed
        }
        return generatedId;
    }

    public ImageDTO getImageDTOById(int imageId) {
        String SQL = "SELECT image_id, property_id, image_url, sort_order FROM Image WHERE image_id = ?";
        ImageDTO imageDTO = null;
        Connection conn = null;
        try {
            conn = DbUtils.getConnection(); // Use DbUtils to get connection
            try (PreparedStatement pstmt = conn.prepareStatement(SQL)) {
                pstmt.setInt(1, imageId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        imageDTO = new ImageDTO(
                            rs.getInt("image_id"),
                            rs.getInt("property_id"),
                            rs.getString("image_url"),
                            rs.getInt("sort_order")
                        );
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println("Lỗi khi lấy hình ảnh theo ID: " + ex.getMessage());
        } finally {
            DbUtils.closeConnection(conn);
        }
        return imageDTO;
    }

    public List<ImageDTO> getImagesDTOByPropertyId(int propertyId) {
        String SQL = "SELECT image_id, property_id, image_url, sort_order FROM Image WHERE property_id = ? ORDER BY sort_order ASC";
        List<ImageDTO> imageDTOs = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DbUtils.getConnection(); // Use DbUtils to get connection
            try (PreparedStatement pstmt = conn.prepareStatement(SQL)) {
                pstmt.setInt(1, propertyId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        imageDTOs.add(new ImageDTO(
                            rs.getInt("image_id"),
                            rs.getInt("property_id"),
                            rs.getString("image_url"),
                            rs.getInt("sort_order")
                        ));
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println("Lỗi khi lấy hình ảnh theo Property ID: " + ex.getMessage());
        } finally {
            DbUtils.closeConnection(conn);
        }
        return imageDTOs;
    }

    public boolean updateImageDTO(ImageDTO imageDTO) {
        String SQL = "UPDATE Image SET property_id = ?, image_url = ?, sort_order = ? WHERE image_id = ?";
        boolean updated = false;
        Connection conn = null;
        try {
            conn = DbUtils.getConnection(); // Use DbUtils to get connection
            try (PreparedStatement pstmt = conn.prepareStatement(SQL)) {
                pstmt.setInt(1, imageDTO.getPropertyId());
                pstmt.setString(2, imageDTO.getImageUrl());
                pstmt.setInt(3, imageDTO.getSortOrder());
                pstmt.setInt(4, imageDTO.getImageId());
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) { updated = true; }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println("Lỗi khi cập nhật hình ảnh: " + ex.getMessage());
        } finally {
            DbUtils.closeConnection(conn);
        }
        return updated;
    }

    public boolean deleteImageDTO(int imageId) {
        String SQL = "DELETE FROM Image WHERE image_id = ?";
        boolean deleted = false;
        Connection conn = null;
        try {
            conn = DbUtils.getConnection(); // Use DbUtils to get connection
            try (PreparedStatement pstmt = conn.prepareStatement(SQL)) {
                pstmt.setInt(1, imageId);
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) { deleted = true; }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println("Lỗi khi xóa hình ảnh: " + ex.getMessage());
        } finally {
            DbUtils.closeConnection(conn);
        }
        return deleted;
    }
}