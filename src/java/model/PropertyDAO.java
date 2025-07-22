package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import utils.DbUtils;

public class PropertyDAO {

    private Connection conn;

    public PropertyDAO(Connection conn) {
        this.conn = conn;
    }

    public PropertyDAO() {
        try {
            this.conn = DbUtils.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Add new property
    public boolean addProperty(PropertyDTO p) {
        String sql = "INSERT INTO Property(name, description, price_per_night, location_id, host_id, "
                + "num_bedrooms, num_bathrooms, num_guests, num_rooms, has_wifi, allows_pets, has_balcony, "
                + "has_parking, has_private_pool, has_ev_station, allows_smoking, near_beach, near_lake, near_river, "
                + "near_countryside, near_city_center, distance_to_beach, distance_to_lake, distance_to_city_center, "
                + "status, thumbnail_url, short_description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try ( Connection conn = DbUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getName());
            ps.setString(2, p.getDescription());
            ps.setBigDecimal(3, p.getPricePerNight());
            ps.setInt(4, p.getLocationId());
            ps.setInt(5, p.getHostId());
            ps.setInt(6, p.getNumBedrooms());
            ps.setInt(7, p.getNumBathrooms());
            ps.setInt(8, p.getNumGuests());
            ps.setInt(9, p.getNumRooms());
            ps.setBoolean(10, p.isHasWifi());
            ps.setBoolean(11, p.isAllowsPets());
            ps.setBoolean(12, p.isHasBalcony());
            ps.setBoolean(13, p.isHasParking());
            ps.setBoolean(14, p.isHasPrivatePool());
            ps.setBoolean(15, p.isHasEvStation());
            ps.setBoolean(16, p.isAllowsSmoking());
            ps.setBoolean(17, p.isNearBeach());
            ps.setBoolean(18, p.isNearLake());
            ps.setBoolean(19, p.isNearRiver());
            ps.setBoolean(20, p.isNearCountryside());
            ps.setBoolean(21, p.isNearCityCenter());
            ps.setBigDecimal(22, p.getDistanceToBeach());
            ps.setBigDecimal(23, p.getDistanceToLake());
            ps.setBigDecimal(24, p.getDistanceToCityCenter());
            ps.setString(25, p.getStatus());
            ps.setString(26, p.getThumbnailUrl());
            ps.setString(27, p.getShortDescription());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete property by ID
    public void deleteProperty(int propertyId) throws SQLException {
        String sql = "DELETE FROM Property WHERE property_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, propertyId);
        ps.executeUpdate();
        ps.close();
    }

    // Update property
    public boolean updateProperty(PropertyDTO p) {
        String sql = "UPDATE Property SET name=?, description=?, price_per_night=?, location_id=?, host_id=?, "
                + "num_bedrooms=?, num_bathrooms=?, num_guests=?, num_rooms=?, has_wifi=?, allows_pets=?, "
                + "has_balcony=?, has_parking=?, has_private_pool=?, has_ev_station=?, allows_smoking=?, "
                + "near_beach=?, near_lake=?, near_river=?, near_countryside=?, near_city_center=?, "
                + "distance_to_beach=?, distance_to_lake=?, distance_to_city_center=?, status=?, thumbnail_url=?, short_description=? "
                + "WHERE property_id = ?";
        try ( Connection conn = DbUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getName());
            ps.setString(2, p.getDescription());
            ps.setBigDecimal(3, p.getPricePerNight());
            ps.setInt(4, p.getLocationId());
            ps.setInt(5, p.getHostId());
            ps.setInt(6, p.getNumBedrooms());
            ps.setInt(7, p.getNumBathrooms());
            ps.setInt(8, p.getNumGuests());
            ps.setInt(9, p.getNumRooms());
            ps.setBoolean(10, p.isHasWifi());
            ps.setBoolean(11, p.isAllowsPets());
            ps.setBoolean(12, p.isHasBalcony());
            ps.setBoolean(13, p.isHasParking());
            ps.setBoolean(14, p.isHasPrivatePool());
            ps.setBoolean(15, p.isHasEvStation());
            ps.setBoolean(16, p.isAllowsSmoking());
            ps.setBoolean(17, p.isNearBeach());
            ps.setBoolean(18, p.isNearLake());
            ps.setBoolean(19, p.isNearRiver());
            ps.setBoolean(20, p.isNearCountryside());
            ps.setBoolean(21, p.isNearCityCenter());
            ps.setBigDecimal(22, p.getDistanceToBeach());
            ps.setBigDecimal(23, p.getDistanceToLake());
            ps.setBigDecimal(24, p.getDistanceToCityCenter());
            ps.setString(25, p.getStatus());
            ps.setString(26, p.getThumbnailUrl());
            ps.setString(27, p.getShortDescription());
            ps.setInt(28, p.getPropertyId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Filter by amenities
    public List<PropertyDTO> filterByAmenities(boolean hasWifi, boolean hasPool, boolean hasParking) throws SQLException {
        String sql = "SELECT * FROM Property WHERE has_wifi = ? AND has_private_pool = ? AND has_parking = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setBoolean(1, hasWifi);
        ps.setBoolean(2, hasPool);
        ps.setBoolean(3, hasParking);

        ResultSet rs = ps.executeQuery();
        List<PropertyDTO> list = new ArrayList<>();
        while (rs.next()) {
            PropertyDTO p = new PropertyDTO();
            p.setPropertyId(rs.getInt("property_id"));
            p.setName(rs.getString("name"));
            p.setPricePerNight(rs.getBigDecimal("price_per_night"));
            p.setDescription(rs.getString("description"));
            list.add(p);
        }
        rs.close();
        ps.close();
        return list;
    }

    // Search by name and guest count
    public List<PropertyDTO> search(String keyword, int guests) throws SQLException {
        String sql = "SELECT * FROM Property WHERE name LIKE ? AND num_guests >= ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, "%" + keyword + "%");
        ps.setInt(2, guests);

        ResultSet rs = ps.executeQuery();
        List<PropertyDTO> result = new ArrayList<>();
        while (rs.next()) {
            PropertyDTO p = new PropertyDTO();
            p.setPropertyId(rs.getInt("property_id"));
            p.setName(rs.getString("name"));
            p.setNumGuests(rs.getInt("num_guests"));
            p.setPricePerNight(rs.getBigDecimal("price_per_night"));
            result.add(p);
        }
        rs.close();
        ps.close();
        return result;
    }

    // Get property by ID
    public PropertyDTO getPropertyById(int id) throws SQLException {
        String sql = "SELECT * FROM Property WHERE property_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        PropertyDTO p = null;
        if (rs.next()) {
            p = new PropertyDTO();
            p.setPropertyId(rs.getInt("property_id"));
            p.setName(rs.getString("name"));
            p.setDescription(rs.getString("description"));
            p.setPricePerNight(rs.getBigDecimal("price_per_night"));
            p.setLocationId(rs.getInt("location_id"));
            p.setHostId(rs.getInt("host_id"));
            p.setNumBedrooms(rs.getInt("num_bedrooms"));
            p.setNumBathrooms(rs.getInt("num_bathrooms"));
            p.setNumGuests(rs.getInt("num_guests"));
            p.setNumRooms(rs.getInt("num_rooms"));
            p.setHasWifi(rs.getBoolean("has_wifi"));
            p.setAllowsPets(rs.getBoolean("allows_pets"));
            p.setHasBalcony(rs.getBoolean("has_balcony"));
            p.setHasParking(rs.getBoolean("has_parking"));
            p.setHasPrivatePool(rs.getBoolean("has_private_pool"));
            p.setHasEvStation(rs.getBoolean("has_ev_station"));
            p.setAllowsSmoking(rs.getBoolean("allows_smoking"));
            p.setNearBeach(rs.getBoolean("near_beach"));
            p.setNearLake(rs.getBoolean("near_lake"));
            p.setNearRiver(rs.getBoolean("near_river"));
            p.setNearCountryside(rs.getBoolean("near_countryside"));
            p.setNearCityCenter(rs.getBoolean("near_city_center"));
            p.setDistanceToBeach(rs.getBigDecimal("distance_to_beach"));
            p.setDistanceToLake(rs.getBigDecimal("distance_to_lake"));
            p.setDistanceToCityCenter(rs.getBigDecimal("distance_to_city_center"));
            p.setStatus(rs.getString("status"));
            p.setThumbnailUrl(rs.getString("thumbnail_url"));
            p.setShortDescription(rs.getString("short_description"));
        }

        rs.close();
        ps.close();
        return p;
    }

//    public List<PropertyDTO> getAllProperties() throws SQLException {
//        String sql = "SELECT * FROM Property";
//        PreparedStatement ps = conn.prepareStatement(sql);
//        ResultSet rs = ps.executeQuery();
//
//        List<PropertyDTO> list = new ArrayList<>();
//        while (rs.next()) {
//            PropertyDTO p = new PropertyDTO();
//            p.setPropertyId(rs.getInt("property_id"));
//            p.setName(rs.getString("name"));
//            p.setDescription(rs.getString("description"));
//            p.setPricePerNight(rs.getBigDecimal("price_per_night"));
//            p.setLocationId(rs.getInt("location_id"));
//            p.setHostId(rs.getInt("host_id"));
//            p.setNumBedrooms(rs.getInt("num_bedrooms"));
//            p.setNumBathrooms(rs.getInt("num_bathrooms"));
//            p.setNumGuests(rs.getInt("num_guests"));
//            p.setNumRooms(rs.getInt("num_rooms"));
//            p.setHasWifi(rs.getBoolean("has_wifi"));
//            p.setAllowsPets(rs.getBoolean("allows_pets"));
//            p.setHasBalcony(rs.getBoolean("has_balcony"));
//            p.setHasParking(rs.getBoolean("has_parking"));
//            p.setHasPrivatePool(rs.getBoolean("has_private_pool"));
//            p.setHasEvStation(rs.getBoolean("has_ev_station"));
//            p.setAllowsSmoking(rs.getBoolean("allows_smoking"));
//            p.setNearBeach(rs.getBoolean("near_beach"));
//            p.setNearLake(rs.getBoolean("near_lake"));
//            p.setNearRiver(rs.getBoolean("near_river"));
//            p.setNearCountryside(rs.getBoolean("near_countryside"));
//            p.setNearCityCenter(rs.getBoolean("near_city_center"));
//            p.setDistanceToBeach(rs.getBigDecimal("distance_to_beach"));
//            p.setDistanceToLake(rs.getBigDecimal("distance_to_lake"));
//            p.setDistanceToCityCenter(rs.getBigDecimal("distance_to_city_center"));
//            p.setStatus(rs.getString("status"));
//            p.setThumbnailUrl(rs.getString("thumbnail_url"));
//            p.setShortDescription(rs.getString("short_description"));
//
//            list.add(p);
//        }
//
//        rs.close();
//        ps.close();
//        return list;
//    }
    public List<PropertyDTO> getAllProperties() throws SQLException {
        String sql = "SELECT * FROM Property";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        List<PropertyDTO> list = new ArrayList<>();
        if (conn == null) {
            System.err.println("❌ Kết nối DB (conn) đang NULL trong getAllProperties()");
            return list;
        }
        while (rs.next()) {
            PropertyDTO p = new PropertyDTO();
            p.setPropertyId(rs.getInt("property_id"));
            p.setName(rs.getString("name"));
            p.setDescription(rs.getString("description"));
            p.setPricePerNight(rs.getBigDecimal("price_per_night"));
            p.setLocationId(rs.getInt("location_id"));
            p.setHostId(rs.getInt("host_id"));
            p.setNumBedrooms(rs.getInt("num_bedrooms"));
            p.setNumBathrooms(rs.getInt("num_bathrooms"));
            p.setNumGuests(rs.getInt("num_guests"));
            p.setNumRooms(rs.getInt("num_rooms"));
            p.setHasWifi(rs.getBoolean("has_wifi"));
            p.setAllowsPets(rs.getBoolean("allows_pets"));
            p.setHasBalcony(rs.getBoolean("has_balcony"));
            p.setHasParking(rs.getBoolean("has_parking"));
            p.setHasPrivatePool(rs.getBoolean("has_private_pool"));
            p.setHasEvStation(rs.getBoolean("has_ev_station"));
            p.setAllowsSmoking(rs.getBoolean("allows_smoking"));
            p.setNearBeach(rs.getBoolean("near_beach"));
            p.setNearLake(rs.getBoolean("near_lake"));
            p.setNearRiver(rs.getBoolean("near_river"));
            p.setNearCountryside(rs.getBoolean("near_countryside"));
            p.setNearCityCenter(rs.getBoolean("near_city_center"));
            p.setDistanceToBeach(rs.getBigDecimal("distance_to_beach"));
            p.setDistanceToLake(rs.getBigDecimal("distance_to_lake"));
            p.setDistanceToCityCenter(rs.getBigDecimal("distance_to_city_center"));
            p.setStatus(rs.getString("status"));
            p.setThumbnailUrl(rs.getString("thumbnail_url"));
            p.setShortDescription(rs.getString("short_description"));

            list.add(p);
        }

        rs.close();
        ps.close();
        return list;
    }

    // PropertyDAO.java
//    public List<PropertyDTO> getPropertiesByHost(int hostId) throws SQLException {
//        String sql = "SELECT * FROM Property WHERE host_id = ?";
//        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
//            ps.setInt(1, hostId);
//            ResultSet rs = ps.executeQuery();
//
//            List<PropertyDTO> list = new ArrayList<>();
//            while (rs.next()) {
//                PropertyDTO p = mapResultSetToPropertyDTO(rs);   // ↙︎ nếu bạn đã có hàm map, dùng lại
//                list.add(p);
//            }
//            return list;
//        }
//    }
    public List<PropertyDTO> getPropertiesByHost(int hostId) throws SQLException {
        List<PropertyDTO> list = new ArrayList<>();
        if (conn == null) {
            System.err.println("❌ Kết nối DB đang null trong getPropertiesByHost()");
            return list;
        }

        String sql = "SELECT * FROM Property WHERE host_id = ?";
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, hostId);
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToPropertyDTO(rs));
                }
            }
        }
        return list;
    }

    private PropertyDTO mapResultSetToPropertyDTO(ResultSet rs) throws SQLException {
        PropertyDTO p = new PropertyDTO();
        p.setPropertyId(rs.getInt("property_id"));
        p.setName(rs.getString("name"));
        p.setDescription(rs.getString("description"));
        p.setPricePerNight(rs.getBigDecimal("price_per_night"));
        p.setLocationId(rs.getInt("location_id"));
        p.setHostId(rs.getInt("host_id"));
        p.setNumBedrooms(rs.getInt("num_bedrooms"));
        p.setNumBathrooms(rs.getInt("num_bathrooms"));
        p.setNumGuests(rs.getInt("num_guests"));
        p.setNumRooms(rs.getInt("num_rooms"));
        p.setHasWifi(rs.getBoolean("has_wifi"));
        p.setAllowsPets(rs.getBoolean("allows_pets"));
        p.setHasBalcony(rs.getBoolean("has_balcony"));
        p.setHasParking(rs.getBoolean("has_parking"));
        p.setHasPrivatePool(rs.getBoolean("has_private_pool"));
        p.setHasEvStation(rs.getBoolean("has_ev_station"));
        p.setAllowsSmoking(rs.getBoolean("allows_smoking"));
        p.setNearBeach(rs.getBoolean("near_beach"));
        p.setNearLake(rs.getBoolean("near_lake"));
        p.setNearRiver(rs.getBoolean("near_river"));
        p.setNearCountryside(rs.getBoolean("near_countryside"));
        p.setNearCityCenter(rs.getBoolean("near_city_center"));
        p.setDistanceToBeach(rs.getBigDecimal("distance_to_beach"));
        p.setDistanceToLake(rs.getBigDecimal("distance_to_lake"));
        p.setDistanceToCityCenter(rs.getBigDecimal("distance_to_city_center"));
        p.setStatus(rs.getString("status"));
        p.setThumbnailUrl(rs.getString("thumbnail_url"));
        p.setShortDescription(rs.getString("short_description"));
        return p;
    }

}
