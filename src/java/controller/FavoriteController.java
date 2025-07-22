package controller;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.FavoriteDAO;
import model.PropertyDTO;
import utils.AuthUtils;

@WebServlet(name = "FavoriteController", urlPatterns = {"/FavoriteController"})
public class FavoriteController extends HttpServlet {

    private static final String ERROR_PAGE = "error.jsp";
    private static final String FAVORITE_LIST_PAGE = "favorite-list.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        FavoriteDAO dao = new FavoriteDAO();

        try {
            switch (action) {
                case "addFavorite":
                    addFavorite(request, response, dao);
                    break;
                case "removeFavorite":
                    removeFavorite(request, response, dao);
                    break;
                case "viewFavorites":
                    viewFavorites(request, response, dao);
                    break;
                default:
                    request.setAttribute("errorMessage", "Hành động không hợp lệ: " + action);
                    request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi trong FavoriteController: " + e.getMessage());
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }
    }

    // ✅ Thêm vào danh sách yêu thích (tối đa 100)
    private void addFavorite(HttpServletRequest request, HttpServletResponse response, FavoriteDAO dao)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Integer userId = AuthUtils.getUserIdFromSession(session);
        if (userId == null) {
            request.setAttribute("message", "Bạn phải đăng nhập để thêm yêu thích.");
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
            return;
        }

        int favoriteCount = dao.countFavoritesByUser(userId);
        if (favoriteCount >= 100) {
            request.setAttribute("message", "Bạn chỉ có thể thêm tối đa 100 bất động sản vào danh sách yêu thích.");
            viewFavorites(request, response, dao);
            return;
        }

        int propertyId = Integer.parseInt(request.getParameter("propertyId"));
        boolean success = dao.addFavorite(userId, propertyId);

        if (success) {
            request.setAttribute("message", "Đã thêm vào danh sách yêu thích!");
        } else {
            request.setAttribute("message", "Đã có trong danh sách hoặc lỗi khi thêm.");
        }

        viewFavorites(request, response, dao);
    }

    // ✅ Xoá khỏi danh sách yêu thích
    private void removeFavorite(HttpServletRequest request, HttpServletResponse response, FavoriteDAO dao)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Integer userId = AuthUtils.getUserIdFromSession(session);
        if (userId == null) {
            request.setAttribute("message", "Bạn phải đăng nhập để xoá yêu thích.");
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
            return;
        }

        int propertyId = Integer.parseInt(request.getParameter("propertyId"));
        boolean success = dao.removeFavorite(userId, propertyId);

        if (success) {
            request.setAttribute("message", "Đã xoá khỏi danh sách yêu thích.");
        } else {
            request.setAttribute("message", "Không tìm thấy trong danh sách hoặc lỗi khi xoá.");
        }

        viewFavorites(request, response, dao);
    }

    // ✅ Hiển thị danh sách yêu thích
    private void viewFavorites(HttpServletRequest request, HttpServletResponse response, FavoriteDAO dao)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Integer userId = AuthUtils.getUserIdFromSession(session);
        if (userId == null) {
            request.setAttribute("message", "Bạn phải đăng nhập để xem danh sách yêu thích.");
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
            return;
        }

        List<PropertyDTO> favorites = dao.getFavoritePropertiesByUser(userId);
        request.setAttribute("favoriteList", favorites);
        request.getRequestDispatcher(FAVORITE_LIST_PAGE).forward(request, response);
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
        return "Controller xử lý danh sách yêu thích";
    }
}
