// src/main/java/com/yourpackage/dao/LocationDAO.java
package model;

import com.yourpackage.dto.LocationDTO;
import utils.DbUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocationDAO {

    public int addLocationDTO(LocationDTO locationDTO) throws ClassNotFoundException {
        String SQL = "INSERT INTO Location (city) VALUES (?)";
        int generatedId = -1;
        try (Connection conn = DbUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, locationDTO.getCity());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedId = rs.getInt(1);
                        locationDTO.setLocationId(generatedId);
                    }
                }
            }
        } catch (SQLException ex) { System.err.println("Lỗi khi thêm địa điểm: " + ex.getMessage()); }
        return generatedId;
    }

    public LocationDTO getLocationDTOById(int locationId) throws ClassNotFoundException {
        String SQL = "SELECT location_id, city FROM Location WHERE location_id = ?";
        LocationDTO locationDTO = null;
        try (Connection conn = DbUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setInt(1, locationId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    locationDTO = new LocationDTO(rs.getInt("location_id"), rs.getString("city"));
                }
            }
        } catch (SQLException ex) { System.err.println("Lỗi khi lấy địa điểm theo ID: " + ex.getMessage()); }
        return locationDTO;
    }

    public List<LocationDTO> getAllLocationDTOs() throws ClassNotFoundException {
        String SQL = "SELECT location_id, city FROM Location";
        List<LocationDTO> locationDTOs = new ArrayList<>();
        try (Connection conn = DbUtils.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {
            while (rs.next()) {
                locationDTOs.add(new LocationDTO(rs.getInt("location_id"), rs.getString("city")));
            }
        } catch (SQLException ex) { System.err.println("Lỗi khi lấy tất cả địa điểm: " + ex.getMessage()); }
        return locationDTOs;
    }

    public boolean updateLocationDTO(LocationDTO locationDTO) throws ClassNotFoundException {
        String SQL = "UPDATE Location SET city = ? WHERE location_id = ?";
        boolean updated = false;
        try (Connection conn = DbUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, locationDTO.getCity());
            pstmt.setInt(2, locationDTO.getLocationId());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) { updated = true; }
        } catch (SQLException ex) { System.err.println("Lỗi khi cập nhật địa điểm: " + ex.getMessage()); }
        return updated;
    }

    public boolean deleteLocationDTO(int locationId) throws ClassNotFoundException {
        String SQL = "DELETE FROM Location WHERE location_id = ?";
        boolean deleted = false;
        try (Connection conn = DbUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setInt(1, locationId);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) { deleted = true; }
        } catch (SQLException ex) { System.err.println("Lỗi khi xóa địa điểm: " + ex.getMessage()); }
        return deleted;
    }
}