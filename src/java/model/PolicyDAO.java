package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import utils.DbUtils;

public class PolicyDAO {

    public List<PolicyDTO> getAllPolicies() {
        List<PolicyDTO> list = new ArrayList<>();
        String sql = "SELECT policy_id, title, description FROM Policy";
        try (Connection conn = DbUtils.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql); 
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new PolicyDTO(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addPolicy(PolicyDTO policy) {
        String sql = "INSERT INTO Policy (title, description) VALUES (?, ?)";
        try (Connection conn = DbUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, policy.getTitle());
            ps.setString(2, policy.getDescription());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updatePolicy(PolicyDTO policy) {
        String sql = "UPDATE Policy SET title=?, description=? WHERE policy_id=?";
        try (Connection conn = DbUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, policy.getTitle());
            ps.setString(2, policy.getDescription());
            ps.setInt(3, policy.getPolicyId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deletePolicy(int policyId) {
        String sql = "DELETE FROM Policy WHERE policy_id=?";
        try (Connection conn = DbUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, policyId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
