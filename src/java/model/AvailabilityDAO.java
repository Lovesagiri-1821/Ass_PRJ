package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import utils.AuthUtils;
import utils.DbUtils;

public class AvailabilityDAO {

    // 1. Lấy danh sách ngày còn trống (is_available = 0)
    public List<Date> getAvailableDates(int propertyId, Date from, Date to) {
        List<Date> availableDates = new ArrayList<>();
        String sql = "SELECT date FROM Availability WHERE property_id = ? AND date BETWEEN ? AND ? AND is_available = 0";
        try ( Connection conn = DbUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, propertyId);
            ps.setDate(2, from);
            ps.setDate(3, to);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                availableDates.add(rs.getDate("date"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return availableDates;
    }

    // 2. Kiểm tra 1 ngày có còn trống không (trống là is_available = 0)
    public boolean isAvailable(int propertyId, Date date) {
        String sql = "SELECT is_available FROM Availability WHERE property_id = ? AND date = ?";
        try ( Connection conn = DbUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, propertyId);
            ps.setDate(2, date);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("is_available") == 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 3. Cập nhật trạng thái (0 = trống, 1 = đã đặt)
    public boolean updateAvailability(HttpServletRequest request, int propertyId, Date date, boolean isAvailable) {
        if (!AuthUtils.isHostOrAdmin(request)) {
            return false;
        }

        String sql = "UPDATE Availability SET is_available = ? WHERE property_id = ? AND date = ?";
        try ( Connection conn = DbUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, isAvailable ? 0 : 1); // true = còn trống → 0
            ps.setInt(2, propertyId);
            ps.setDate(3, date);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 4. Đặt phòng: đánh dấu is_available = 1 (đã đặt)
    public void reserveDates(int propertyId, Date from, Date to) {
        String sql = "UPDATE Availability SET is_available = 1 WHERE property_id = ? AND date BETWEEN ? AND ?";
        try ( Connection conn = DbUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, propertyId);
            ps.setDate(2, from);
            ps.setDate(3, to);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 5. Hủy phòng: đánh dấu lại là trống (is_available = 0)
    public void releaseDates(int propertyId, Date from, Date to) {
        String sql = "UPDATE Availability SET is_available = 0 WHERE property_id = ? AND date BETWEEN ? AND ?";
        try ( Connection conn = DbUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, propertyId);
            ps.setDate(2, from);
            ps.setDate(3, to);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 6. Lấy giá của 1 ngày
    public double getPrice(int propertyId, Date date) {
        String sql = "SELECT price FROM Availability WHERE property_id = ? AND date = ?";
        try ( Connection conn = DbUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, propertyId);
            ps.setDate(2, date);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("price");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 7. Cập nhật giá (chỉ host hoặc admin)
    public boolean setPrice(HttpServletRequest request, int propertyId, Date date, double price) {
        if (!AuthUtils.isHostOrAdmin(request)) {
            return false;
        }

        String sql = "UPDATE Availability SET price = ? WHERE property_id = ? AND date = ?";
        try ( Connection conn = DbUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, price);
            ps.setInt(2, propertyId);
            ps.setDate(3, date);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 8. Thêm ngày mới (mặc định là chưa đặt → is_available = 0)
    public boolean insertAvailability(HttpServletRequest request, int propertyId, Date date, double price) {
        if (!AuthUtils.isHostOrAdmin(request)) {
            return false;
        }

        String sql = "INSERT INTO Availability(property_id, date, is_available, price) VALUES (?, ?, 0, ?)";
        try ( Connection conn = DbUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, propertyId);
            ps.setDate(2, date);
            ps.setDouble(3, price);
            ps.executeUpdate();
            return true;
        } catch (SQLIntegrityConstraintViolationException ignore) {
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 9. Lấy tất cả availability theo property
    public List<AvailabilityDTO> getAvailabilityByProperty(HttpServletRequest request, int propertyId) {
        if (!AuthUtils.isHostOrAdmin(request)) {
            return new ArrayList<>();
        }

        List<AvailabilityDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM Availability WHERE property_id = ? ORDER BY date";
        try ( Connection conn = DbUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, propertyId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AvailabilityDTO dto = new AvailabilityDTO(
                        rs.getInt("property_id"),
                        rs.getDate("date"),
                        rs.getInt("is_available") == 1, // đã đặt
                        rs.getDouble("price")
                );
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 10. Lấy danh sách ngày đã được đặt
    public List<LocalDate> getUnavailableDates(int propertyId) {
        List<LocalDate> list = new ArrayList<>();
        String sql = "SELECT date FROM Availability WHERE property_id = ? AND is_available = 1 AND date >= CAST(GETDATE() AS DATE)";
        try ( Connection conn = DbUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, propertyId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getDate("date").toLocalDate());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Date> getAvailableDatesBetweenTodayAndFuture(int propertyId) {
        List<Date> list = new ArrayList<>();
        String sql = "SELECT date FROM Availability WHERE property_id = ? AND is_available = 0 AND date >= CAST(GETDATE() AS DATE)";
        try ( Connection conn = DbUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, propertyId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getDate("date"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
