package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import model.*;
import utils.AuthUtils;

@WebServlet(name = "ReviewController", urlPatterns = {"/ReviewController"})
public class ReviewController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        ReviewDAO reviewDao = new ReviewDAO();
        BookingDAO bookingDao = new BookingDAO();

        try {
            switch (action) {
                case "addReview":
                    if (!AuthUtils.isCustomerOrAdmin(request)) {
                        denyAccess(request, response, "thêm đánh giá");
                        return;
                    }
                    handleAddReview(request, response, reviewDao, bookingDao);
                    break;

                case "updateReview":
                    if (!AuthUtils.isCustomerOrAdmin(request)) {
                        denyAccess(request, response, "cập nhật đánh giá");
                        return;
                    }
                    handleUpdateReview(request, response, reviewDao);
                    break;

                case "deleteReview":
                    if (!AuthUtils.isCustomerOrAdmin(request)) {
                        denyAccess(request, response, "xóa đánh giá");
                        return;
                    }
                    handleDeleteReview(request, response, reviewDao);
                    break;

                case "viewReviewsByProperty":
                    handleViewReviewsByProperty(request, response, reviewDao);
                    break;

                case "viewReviewsByUser":
                    handleViewReviewsByUser(request, response, reviewDao);
                    break;

                    case "editForm":
    if (!AuthUtils.isCustomerOrAdmin(request)) {
        denyAccess(request, response, "sửa đánh giá");
        return;
    }
    handleEditForm(request, response, reviewDao);
    break;


                default:
                    request.setAttribute("errorMessage", "Hành động không hợp lệ: " + action);
                    request.getRequestDispatcher("error.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi hệ thống: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    private void handleAddReview(HttpServletRequest request, HttpServletResponse response,
                                 ReviewDAO reviewDao, BookingDAO bookingDao)
            throws ServletException, IOException {
        int propertyId = Integer.parseInt(request.getParameter("propertyId"));
        int rating = Integer.parseInt(request.getParameter("rating"));
        String comment = request.getParameter("comment");
        int userId = AuthUtils.getCurrentUser(request).getUserID();

        // Chỉ admin hoặc user đã từng đặt phòng mới được thêm review
        boolean isAdmin = AuthUtils.isAdmin(request);
        boolean hasBooked = bookingDao.hasBookedProperty(userId, propertyId);

        if (!isAdmin && !hasBooked) {
            denyAccess(request, response, "thêm đánh giá (chỉ khách đã từng đặt phòng)");
            return;
        }

        ReviewDTO review = new ReviewDTO(0, userId, propertyId, rating, comment, null);
        reviewDao.addReviewDTO(review);

        response.sendRedirect("MainController?action=viewPropertyDetail&propertyId=" + propertyId);
    }

    private void handleUpdateReview(HttpServletRequest request, HttpServletResponse response, ReviewDAO dao)
            throws ServletException, IOException {
        int reviewId = Integer.parseInt(request.getParameter("reviewId"));
        int rating = Integer.parseInt(request.getParameter("rating"));
        String comment = request.getParameter("comment");
        int propertyId = Integer.parseInt(request.getParameter("propertyId"));

        ReviewDTO dto = new ReviewDTO();
        dto.setReviewId(reviewId);
        dto.setRating(rating);
        dto.setComment(comment);

        dao.updateReviewDTO(dto);

        response.sendRedirect("MainController?action=viewPropertyDetail&propertyId=" + propertyId);
    }

    private void handleDeleteReview(HttpServletRequest request, HttpServletResponse response, ReviewDAO dao)
            throws ServletException, IOException {
        int reviewId = Integer.parseInt(request.getParameter("reviewId"));
        int propertyId = Integer.parseInt(request.getParameter("propertyId"));

        dao.deleteReviewDTO(reviewId);

        response.sendRedirect("MainController?action=viewPropertyDetail&propertyId=" + propertyId);
    }

    private void handleViewReviewsByProperty(HttpServletRequest request, HttpServletResponse response, ReviewDAO dao)
            throws ServletException, IOException {
        int propertyId = Integer.parseInt(request.getParameter("propertyId"));
        List<ReviewDTO> reviews = dao.getReviewsDTOByPropertyId(propertyId);

        request.setAttribute("reviews", reviews);
        request.getRequestDispatcher("property-detail.jsp").forward(request, response);
    }

    private void handleViewReviewsByUser(HttpServletRequest request, HttpServletResponse response, ReviewDAO dao)
            throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        List<ReviewDTO> reviews = dao.getReviewsDTOByUserId(userId);

        request.setAttribute("reviews", reviews);
        request.getRequestDispatcher("property-detail.jsp").forward(request, response);
    }

    private void denyAccess(HttpServletRequest request, HttpServletResponse response, String action)
            throws ServletException, IOException {
        request.setAttribute("message", AuthUtils.getAccessDeniedMessage(action));
        request.getRequestDispatcher("error.jsp").forward(request, response);
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
        return "ReviewController xử lý thêm, sửa, xóa, xem đánh giá";
    }

    private void handleEditForm(HttpServletRequest request, HttpServletResponse response, ReviewDAO dao)
        throws ServletException, IOException {
    int reviewId = Integer.parseInt(request.getParameter("reviewId"));
    ReviewDTO review = dao.getReviewDTOById(reviewId);
    request.setAttribute("review", review);
    request.getRequestDispatcher("editReview.jsp").forward(request, response);
}

}
