/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import utils.DbUtils;

public class FavoriteDAO {

    // ✅ 1. Thêm vào danh sách yêu thích
    public boolean addFavorite(int userId, int propertyId) {
        // Kiểm tra nếu đã đạt tối đa 100 mục yêu thích
        if (countFavoritesByUser(userId) >= 100) {
            System.err.println("⚠️ User ID " + userId + " đã đạt giới hạn 100 mục yêu thích.");
            return false;
        }

        String sql = "INSERT INTO Favorite (user_id, property_id) VALUES (?, ?)";
        try ( Connection conn = DbUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, propertyId);
            ps.executeUpdate();
            return true;
        } catch (SQLIntegrityConstraintViolationException e) {
            // Trường hợp đã tồn tại thì không thêm nữa
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ✅ 2. Xóa khỏi danh sách yêu thích
    public boolean removeFavorite(int userId, int propertyId) {
        String sql = "DELETE FROM Favorite WHERE user_id = ? AND property_id = ?";
        try ( Connection conn = DbUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, propertyId);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ✅ 3. Lấy danh sách các property được user yêu thích
    public List<FavoriteDTO> getFavoritesByUser(int userId) {
        List<FavoriteDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM Favorite WHERE user_id = ?";
        try ( Connection conn = DbUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                FavoriteDTO dto = new FavoriteDTO(
                        rs.getInt("user_id"),
                        rs.getInt("property_id")
                );
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ✅ 4. Kiểm tra một property có nằm trong danh sách yêu thích của user không
    public boolean isFavorite(int userId, int propertyId) {
        String sql = "SELECT 1 FROM Favorite WHERE user_id = ? AND property_id = ?";
        try ( Connection conn = DbUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, propertyId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ✅ 5. Đếm tổng số user đã thêm 1 property vào danh sách yêu thích (tùy chọn)
    public int countFavoritesByProperty(int propertyId) {
        String sql = "SELECT COUNT(*) AS total FROM Favorite WHERE property_id = ?";
        try ( Connection conn = DbUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, propertyId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<PropertyDTO> getFavoritePropertiesByUser(int userId) {
        List<PropertyDTO> list = new ArrayList<>();
        String sql
                = "SELECT p.* "
                + "FROM Favorite f "
                + "JOIN Property p ON f.property_id = p.property_id "
                + "WHERE f.user_id = ? "
                + "ORDER BY p.created_at DESC";

        try ( Connection conn = DbUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                PropertyDTO property = new PropertyDTO();

                property.setPropertyId(rs.getInt("property_id"));
                property.setName(rs.getString("name"));
                property.setDescription(rs.getString("description"));
                property.setPricePerNight(rs.getBigDecimal("price_per_night"));
                property.setLocationId(rs.getInt("location_id"));
                property.setHostId(rs.getInt("host_id"));
                property.setNumBedrooms(rs.getInt("num_bedrooms"));
                property.setNumBathrooms(rs.getInt("num_bathrooms"));
                property.setNumGuests(rs.getInt("num_guests"));
                property.setNumRooms(rs.getInt("num_rooms"));

                property.setHasWifi(rs.getBoolean("has_wifi"));
                property.setAllowsPets(rs.getBoolean("allows_pets"));
                property.setHasBalcony(rs.getBoolean("has_balcony"));
                property.setHasParking(rs.getBoolean("has_parking"));
                property.setHasPrivatePool(rs.getBoolean("has_private_pool"));
                property.setHasEvStation(rs.getBoolean("has_ev_station"));
                property.setAllowsSmoking(rs.getBoolean("allows_smoking"));

                property.setNearBeach(rs.getBoolean("near_beach"));
                property.setNearLake(rs.getBoolean("near_lake"));
                property.setNearRiver(rs.getBoolean("near_river"));
                property.setNearCountryside(rs.getBoolean("near_countryside"));
                property.setNearCityCenter(rs.getBoolean("near_city_center"));

                property.setDistanceToBeach(rs.getBigDecimal("distance_to_beach"));
                property.setDistanceToLake(rs.getBigDecimal("distance_to_lake"));
                property.setDistanceToCityCenter(rs.getBigDecimal("distance_to_city_center"));

                property.setStatus(rs.getString("status"));
                property.setCreatedAt(rs.getTimestamp("created_at"));
                property.setThumbnailUrl(rs.getString("thumbnail_url"));
                property.setShortDescription(rs.getString("short_description"));

                list.add(property);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public int countFavoritesByUser(int userId) {
        String sql = "SELECT COUNT(*) FROM Favorite WHERE user_id = ?";
        int count = 0;
        try ( Connection conn = DbUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

}
