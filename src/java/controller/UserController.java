package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.*;
import utils.AuthUtils;
import utils.PasswordUtils;

@WebServlet(name = "UserController", urlPatterns = {"/UserController"})
public class UserController extends HttpServlet {

    private static final String LOGIN_PAGE = "login.jsp";
    private static final String WELCOME_PAGE = "welcome.jsp";
    private static final String USER_LIST_PAGE = "user-list.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = LOGIN_PAGE;

        try {
            String action = request.getParameter("action");
            UserDAO userDAO = new UserDAO();
            HttpSession session = request.getSession(false);
            UserDTO currentUser = (session != null) ? (UserDTO) session.getAttribute("user") : null;

            switch (action) {
                case "login":
                    url = handleLogin(request, userDAO);
                    break;
                case "logout":
                    url = handleLogout(request);
                    break;
                case "list":
                    url = handleListUsers(request, response, userDAO);
                    break;
                case "add":
                    url = handleAddUser(request, response, userDAO);
                    break;
                case "update":
                    url = handleUpdateUser(request, response, userDAO, currentUser);
                    break;
                case "delete":
                    url = handleDeleteUser(request, response, userDAO, currentUser);
                    break;
                case "showUpdatePage":
                    url = handleShowUpdatePage(request, userDAO);
                    break;

                case "register":
                    url = handleRegister(request, userDAO);
                    break;

                default:
                    request.setAttribute("message", "Hành động không hợp lệ: " + action);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Lỗi hệ thống: " + e.getMessage());
        } finally {
            if (url != null) {
                request.getRequestDispatcher(url).forward(request, response);
            }
        }
    }

    /**
     * Xử lý đăng nhập
     */
//    private String handleLogin(HttpServletRequest request, UserDAO userDAO) throws SQLException {
//        String email = request.getParameter("strEmail");
//        String password = request.getParameter("strPassword");
//        HttpSession session = request.getSession();
//
//        UserDTO user = userDAO.getUserDTOByEmail(email);
//
//        if (user != null && user.getPasswordHash().equals(password) && !user.isDeleted()) {
//            session.setAttribute("user", user);
//
//            List<PropertyDTO> properties = new PropertyDAO().getAllProperties();
//            request.setAttribute("properties", properties);
//
//            List<PolicyDTO> policies = new PolicyDAO().getAllPolicies();
//            request.setAttribute("policies", policies);
//
//            return WELCOME_PAGE;
//        } else {
//            request.setAttribute("message", "Email hoặc mật khẩu không đúng hoặc tài khoản đã bị xóa.");
//            return LOGIN_PAGE;
//        }
//    }
    private String handleLogin(HttpServletRequest request, UserDAO userDAO) throws SQLException {
        String email = request.getParameter("strEmail");
        String password = request.getParameter("strPassword");
        HttpSession session = request.getSession();

        UserDTO user = userDAO.getUserDTOByEmail(email);

        // So sánh bằng BCrypt
        if (user != null && PasswordUtils.verifyPassword(password, user.getPasswordHash()) && !user.isDeleted()) {
            session.setAttribute("user", user);

            List<PropertyDTO> properties = new PropertyDAO().getAllProperties();
            session.setAttribute("properties", properties);

            List<PolicyDTO> policies = new PolicyDAO().getAllPolicies();
            session.setAttribute("policies", policies); 

            return WELCOME_PAGE;
        } else {
            request.setAttribute("message", "Email hoặc mật khẩu không đúng hoặc tài khoản đã bị xóa.");
            return LOGIN_PAGE;
        }
    }

    /**
     * Xử lý logout
     */
    private String handleLogout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return LOGIN_PAGE;
    }

    /**
     * Hiển thị danh sách người dùng
     */
    private String handleListUsers(HttpServletRequest request, HttpServletResponse response, UserDAO userDAO)
            throws ServletException, IOException {
        UserDTO currentUser = AuthUtils.getCurrentUser(request);
        List<UserDTO> userList = new ArrayList<>();

        if (currentUser != null) {
            if (AuthUtils.isAdmin(request)) {
                userList = userDAO.getAllUserDTOs();
            } else {
                userList.add(userDAO.getUserDTOById(currentUser.getUserID()));
            }
        }

        request.setAttribute("userList", userList);
        request.getRequestDispatcher(USER_LIST_PAGE).forward(request, response);
        return null; // đã forward
    }

    /**
     * Xử lý thêm người dùng
     */
    private String handleAddUser(HttpServletRequest request, HttpServletResponse response, UserDAO userDAO)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        UserDTO newUser = new UserDTO();
        newUser.setName(name);
        newUser.setEmail(email);
//        newUser.setPasswordHash(password);
        newUser.setPasswordHash(PasswordUtils.hashPassword(password)); // ✅ Hash mật khẩu

        newUser.setRole(role);
        newUser.setDeleted(false);

        boolean success = userDAO.addUserDTO(newUser) != -1;
        request.setAttribute("message", success ? "Thêm người dùng thành công!" : "Không thể thêm người dùng.");

        return handleListUsers(request, response, userDAO);
    }

    /**
     * Xử lý cập nhật người dùng
     */
    private String handleUpdateUser(HttpServletRequest request, HttpServletResponse response, UserDAO userDAO, UserDTO currentUser)
            throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        boolean isDeleted = "1".equals(request.getParameter("isDeleted"));

        // Nếu không phải admin, giữ nguyên role cũ
        if (currentUser != null && !"admin".equalsIgnoreCase(currentUser.getRole())) {
            UserDTO oldUser = userDAO.getUserDTOById(userId);
            role = oldUser != null ? oldUser.getRole() : role;
        }

        // Lấy user cũ để giữ lại mật khẩu nếu không nhập mới
        UserDTO oldUser = userDAO.getUserDTOById(userId);
//        String passwordHash = (password != null && !password.trim().isEmpty()) ? password : (oldUser != null ? oldUser.getPasswordHash() : "");

        String passwordHash = (password != null && !password.trim().isEmpty())
                ? PasswordUtils.hashPassword(password)
                : (oldUser != null ? oldUser.getPasswordHash() : "");

        UserDTO updatedUser = new UserDTO();
        updatedUser.setUserID(userId);
        updatedUser.setName(name);
        updatedUser.setEmail(email);
        updatedUser.setPasswordHash(passwordHash);
        updatedUser.setRole(role);
        updatedUser.setDeleted(isDeleted);

        boolean success = userDAO.updateUserDTO(updatedUser);
        request.setAttribute("message", success ? "Cập nhật người dùng thành công!" : "Không thể cập nhật người dùng.");

        return handleListUsers(request, response, userDAO);
    }

    /**
     * Xử lý xóa người dùng
     */
    private String handleDeleteUser(HttpServletRequest request, HttpServletResponse response, UserDAO userDAO, UserDTO currentUser)
            throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        boolean success;

        if (currentUser != null && "admin".equalsIgnoreCase(currentUser.getRole())) {
            // Admin: xóa vĩnh viễn
            success = userDAO.deleteUserPermanently(userId);
            request.setAttribute("message", success ? "Đã xóa vĩnh viễn người dùng." : "Xóa thất bại.");
        } else {
            // Host, Customer: xóa mềm; nếu tự xóa chính mình thì logout
            success = userDAO.softDeleteUserDTO(userId);
            if (success && currentUser != null && currentUser.getUserID() == userId) {
                request.getSession().invalidate();
                request.setAttribute("message", "Tài khoản của bạn đã bị xóa.");
                return LOGIN_PAGE;
            } else {
                request.setAttribute("message", success ? "Đã xóa người dùng." : "Xóa thất bại.");
            }
        }
        return handleListUsers(request, response, userDAO);
    }

    /**
     * Hiển thị trang update-user.jsp
     */
    private String handleShowUpdatePage(HttpServletRequest request, UserDAO userDAO) {
        int userId = Integer.parseInt(request.getParameter("userId"));
        UserDTO user = userDAO.getUserDTOById(userId);
        request.setAttribute("user", user);
        return "update-user.jsp";
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "UserController: login, logout, quản lý user với phân quyền admin/host/customer";
    }

// Trong UserController
//    private String handleRegister(HttpServletRequest request, UserDAO userDAO)
//            throws ServletException, IOException {
//        String name = request.getParameter("name");
//        String email = request.getParameter("email");
//        String password = request.getParameter("password");
//
//        if (name == null || email == null || password == null
//                || name.trim().isEmpty() || email.trim().isEmpty() || password.trim().isEmpty()) {
//            request.setAttribute("message", "Vui lòng điền đầy đủ thông tin.");
//            return "register.jsp";
//        }
//
//        // Kiểm tra email đã tồn tại chưa
//        UserDTO existingUser = userDAO.getUserDTOByEmail(email);
//        if (existingUser != null) {
//            request.setAttribute("message", "Email đã được sử dụng.");
//            return "register.jsp";
//        }
//
////        UserDTO newUser = new UserDTO(name, email, password, "customer");
//        UserDTO newUser = new UserDTO(
//                name,
//                email,
//                PasswordUtils.hashPassword(password), // ✅ Hash tại đây
//                "customer"
//        );
//
//        int newId = userDAO.addUserDTO(newUser);
//
//        if (newId != -1) {
//            request.setAttribute("message", "Đăng ký thành công! Vui lòng đăng nhập.");
//            return "login.jsp";
//        } else {
//            request.setAttribute("message", "Đăng ký thất bại. Thử lại sau.");
//            return "register.jsp";
//        }
//    }
    private String handleRegister(HttpServletRequest request, UserDAO userDAO)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        if (name == null || email == null || password == null || role == null
                || name.trim().isEmpty() || email.trim().isEmpty() || password.trim().isEmpty()) {
            request.setAttribute("message", "Vui lòng điền đầy đủ thông tin.");
            return "register.jsp";
        }

        // Validate role hợp lệ
        if (!"customer".equalsIgnoreCase(role) && !"host".equalsIgnoreCase(role)) {
            role = "customer"; // fallback nếu có hack
        }

        // Kiểm tra email đã tồn tại
        UserDTO existingUser = userDAO.getUserDTOByEmail(email);
        if (existingUser != null) {
            request.setAttribute("message", "Email đã được sử dụng.");
            return "register.jsp";
        }

        // Tạo user mới
        UserDTO newUser = new UserDTO(
                name,
                email,
                PasswordUtils.hashPassword(password),
                role
        );

        int newId = userDAO.addUserDTO(newUser);

        if (newId != -1) {
            request.setAttribute("message", "Đăng ký thành công! Vui lòng đăng nhập.");
            return "login.jsp";
        } else {
            request.setAttribute("message", "Đăng ký thất bại. Thử lại sau.");
            return "register.jsp";
        }
    }

}
