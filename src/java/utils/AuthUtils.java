package utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import model.UserDTO;

public class AuthUtils {

    public static Integer getUserIdFromSession(HttpSession session) {
        if (session == null) {
            return null;
        }
        Object userId = session.getAttribute("userId");
        return (userId instanceof Integer) ? (Integer) userId : null;
    }

    public static UserDTO getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // Thay đổi thành getSession(false)
        if (session != null) {
            return (UserDTO) session.getAttribute("user");
        }
        return null;
    }

    public static boolean isLoggedIn(HttpServletRequest request) {
        return getCurrentUser(request) != null;
    }

    public static boolean hasRole(HttpServletRequest request, String role) {
        UserDTO user = getCurrentUser(request);
        if (user != null) {
            String userRole = user.getRole();
            return userRole != null && userRole.equalsIgnoreCase(role);
        }
        return false;
    }

    public static boolean isAdmin(HttpServletRequest request) {
        return hasRole(request, "admin");
    }

    public static boolean isHost(HttpServletRequest request) {
        return hasRole(request, "host");
    }

    public static boolean isCustomer(HttpServletRequest request) {
        return hasRole(request, "customer");
    }

    public static boolean isHostOrAdmin(HttpServletRequest request) {
        return isHost(request) || isAdmin(request);
    }

    public static boolean isCustomerOrAdmin(HttpServletRequest request) {
        return isCustomer(request) || isAdmin(request);
    }

    public static String getLoginURL() {
        return "MainController?action=login"; // Hoặc "login.jsp" tùy vào luồng đăng nhập của bạn
    }

    public static String getAccessDeniedMessage(String action) {
        return "Bạn không có quyền để " + action + ". Vui lòng liên hệ quản trị viên."; // Đã Việt hóa
    }
}
