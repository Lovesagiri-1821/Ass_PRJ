package model;

import utils.DbUtils;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) quản lý bảng [User]
 */
public class UserDAO {

    /**
     * Thêm người dùng mới vào cơ sở dữ liệu.
     *
     * @param userDTO Đối tượng người dùng.
     * @return ID mới nếu thành công, -1 nếu lỗi.
     */
    public int addUserDTO(UserDTO userDTO) {
        String sql = "INSERT INTO [User] (name, email, password_hash, role, is_deleted) VALUES (?, ?, ?, ?, ?)";
        int generatedId = -1;
        Connection conn = null;

        try {
            conn = DbUtils.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, userDTO.getName());
                ps.setString(2, userDTO.getEmail());
                ps.setString(3, userDTO.getPasswordHash());
                ps.setString(4, userDTO.getRole());
                ps.setBoolean(5, userDTO.isDeleted());

                int affectedRows = ps.executeUpdate();
                if (affectedRows > 0) {
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            generatedId = rs.getInt(1);
                            userDTO.setUserID(generatedId);
                        }
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println("Lỗi khi thêm người dùng: " + ex.getMessage());
        } finally {
            DbUtils.closeConnection(conn);
        }
        return generatedId;
    }

    /**
     * Lấy người dùng theo ID.
     */
    public UserDTO getUserDTOById(int userId) {
        String sql = "SELECT user_id, name, email, password_hash, role, is_deleted, created_at FROM [User] WHERE user_id = ?";
        UserDTO user = null;
        Connection conn = null;

        try {
            conn = DbUtils.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, userId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        user = mapResultSetToUserDTO(rs);
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println("Lỗi khi lấy người dùng theo ID: " + ex.getMessage());
        } finally {
            DbUtils.closeConnection(conn);
        }
        return user;
    }

    /**
     * Lấy người dùng theo email.
     */
    public UserDTO getUserDTOByEmail(String email) {
        String sql = "SELECT user_id, name, email, password_hash, role, is_deleted, created_at FROM [User] WHERE email = ?";
        UserDTO user = null;
        Connection conn = null;

        try {
            conn = DbUtils.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, email);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        user = mapResultSetToUserDTO(rs);
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println("Lỗi khi lấy người dùng theo email: " + ex.getMessage());
        } finally {
            DbUtils.closeConnection(conn);
        }
        return user;
    }

    /**
     * Lấy tất cả người dùng.
     */
    public List<UserDTO> getAllUserDTOs() {
        String sql = "SELECT user_id, name, email, password_hash, role, is_deleted, created_at FROM [User]";
        List<UserDTO> list = new ArrayList<>();
        Connection conn = null;

        try {
            conn = DbUtils.getConnection();
            try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    list.add(mapResultSetToUserDTO(rs));
                }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println("Lỗi khi lấy tất cả người dùng: " + ex.getMessage());
        } finally {
            DbUtils.closeConnection(conn);
        }
        return list;
    }

    /**
     * Cập nhật thông tin người dùng.
     */
    public boolean updateUserDTO(UserDTO userDTO) {
        String sql = "UPDATE [User] SET name = ?, email = ?, password_hash = ?, role = ?, is_deleted = ? WHERE user_id = ?";
        boolean success = false;
        Connection conn = null;

        try {
            conn = DbUtils.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, userDTO.getName());
                ps.setString(2, userDTO.getEmail());
                ps.setString(3, userDTO.getPasswordHash());
                ps.setString(4, userDTO.getRole());
                ps.setBoolean(5, userDTO.isDeleted());
                ps.setInt(6, userDTO.getUserID());

                success = ps.executeUpdate() > 0;
            }
        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println("Lỗi khi cập nhật người dùng: " + ex.getMessage());
        } finally {
            DbUtils.closeConnection(conn);
        }
        return success;
    }

    /**
     * Xóa mềm người dùng (đặt is_deleted = 1).
     */
    public boolean softDeleteUserDTO(int userId) {
        String sql = "UPDATE [User] SET is_deleted = 1 WHERE user_id = ?";
        boolean success = false;
        Connection conn = null;

        try {
            conn = DbUtils.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, userId);
                success = ps.executeUpdate() > 0;
            }
        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println("Lỗi khi xóa mềm người dùng: " + ex.getMessage());
        } finally {
            DbUtils.closeConnection(conn);
        }
        return success;
    }

    /**
     * Xóa vĩnh viễn người dùng khỏi cơ sở dữ liệu.
     */
    public boolean deleteUserPermanently(int userId) {
        String sql = "DELETE FROM [User] WHERE user_id = ?";
        boolean success = false;
        Connection conn = null;

        try {
            conn = DbUtils.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, userId);
                success = ps.executeUpdate() > 0;
            }
        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println("Lỗi khi xóa vĩnh viễn người dùng: " + ex.getMessage());
        } finally {
            DbUtils.closeConnection(conn);
        }
        return success;
    }

    /**
     * Hàm tiện ích: chuyển ResultSet thành UserDTO.
     */
    private UserDTO mapResultSetToUserDTO(ResultSet rs) throws SQLException {
        UserDTO user = new UserDTO();
        user.setUserID(rs.getInt("user_id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setPasswordHash(rs.getString("password_hash"));
        user.setRole(rs.getString("role"));
        user.setDeleted(rs.getBoolean("is_deleted"));
        Timestamp ts = rs.getTimestamp("created_at");
        user.setCreatedAt(ts != null ? ts.toLocalDateTime() : null);
        return user;
    }
}
