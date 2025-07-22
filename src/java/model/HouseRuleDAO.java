package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HouseRuleDAO {

    private Connection getConnection() throws SQLException {
        // Thay bằng thông tin kết nối thực tế
        return DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=Assignment2;user=sa;password=12345");
    }

    // Lấy tất cả house rules (dành cho admin)
    public List<HouseRuleDTO> getAllHouseRules() {
        List<HouseRuleDTO> list = new ArrayList<>();
        String sql = "SELECT rule_id, property_id, rule_text FROM HouseRule";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new HouseRuleDTO(rs.getInt(1), rs.getInt(2), rs.getString(3)));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    // Lấy house rules của 1 host (hostId) – cần liên kết property với host
    public List<HouseRuleDTO> getHouseRulesByHost(int hostId) {
        List<HouseRuleDTO> list = new ArrayList<>();
        String sql = "SELECT hr.rule_id, hr.property_id, hr.rule_text " +
                     "FROM HouseRule hr " +
                     "JOIN Property p ON hr.property_id = p.property_id " +
                     "WHERE p.host_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, hostId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new HouseRuleDTO(rs.getInt(1), rs.getInt(2), rs.getString(3)));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    // Thêm mới rule
    public boolean addHouseRule(HouseRuleDTO rule) {
        String sql = "INSERT INTO HouseRule (property_id, rule_text) VALUES (?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, rule.getPropertyId());
            ps.setString(2, rule.getRuleText());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    // Cập nhật rule text theo rule_id
    public boolean updateHouseRule(HouseRuleDTO rule) {
        String sql = "UPDATE HouseRule SET rule_text=? WHERE rule_id=?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, rule.getRuleText());
            ps.setInt(2, rule.getRuleId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    // Xóa rule theo rule_id
    public boolean deleteHouseRule(int ruleId) {
        String sql = "DELETE FROM HouseRule WHERE rule_id=?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ruleId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public HouseRuleDTO getHouseRuleById(int ruleId) {
    String sql = "SELECT rule_id, property_id, rule_text FROM HouseRule WHERE rule_id=?";
    try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, ruleId);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return new HouseRuleDTO(rs.getInt(1), rs.getInt(2), rs.getString(3));
            }
        }
    } catch (SQLException e) { e.printStackTrace(); }
    return null;
}

public List<HouseRuleDTO> getHouseRulesByProperty(int propertyId) {
    List<HouseRuleDTO> list = new ArrayList<>();
    String sql = "SELECT rule_id, property_id, rule_text FROM HouseRule WHERE property_id=?";
    try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, propertyId);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new HouseRuleDTO(rs.getInt(1), rs.getInt(2), rs.getString(3)));
            }
        }
    } catch (SQLException e) { e.printStackTrace(); }
    return list;
}


}
