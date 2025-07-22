package controller;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import model.AvailabilityDAO;
import model.ImageDAO;
import model.ImageDTO;
import model.PolicyDAO;
import model.PolicyDTO;
import model.PropertyDAO;
import model.PropertyDTO;
import model.ReviewDAO;
import model.ReviewDTO;
import utils.DbUtils;

@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {

    private static final String LOGIN_PAGE = "login.jsp";
    private static final String WELCOME_PAGE = "welcome.jsp";
    private static final String BOOK_CONTROLLER = "BookingController";
    private static final String USER_CONTROLLER = "UserController";
    private static final String PROPERTY_CONTROLLER = "PropertyController";
    private static final String IMAGE_CONTROLLER = "ImageController";
    private static final String AVAILABILITY_CONTROLLER = "AvailabilityController";
    private static final String REVIEW_CONTROLLER = "ReviewController";
    private static final String FAVORITE_CONTROLLER = "FavoriteController";
    private static final String POLICY_CONTROLLER = "PolicyController";

    private boolean isUserAction(String action) {
        return action != null && (action.equals("login")
                || action.equals("logout")
                || action.equals("add")
                || action.equals("update")
                || action.equals("delete")
                || action.equals("list"));
    }

    private boolean isPropertyAction(String action) {
        return action != null && (action.startsWith("property") || action.equals("propertyFilter"));
    }

    private boolean isImageAction(String action) {
        return action != null && action.startsWith("image");
    }

    private boolean isBookingAction(String action) {
        return action != null && (action.equals("bookForm")
                || action.equals("confirmBooking")
                || action.equals("listUserBookings")
                || action.equals("showBooking"));
    }

    private boolean isAvailabilityAction(String action) {
        return action != null && (action.equals("addAvailability")
                || action.equals("updateAvailability")
                || action.equals("setPrice")
                || action.equals("viewAvailability"));
    }

    private boolean isReviewAction(String action) {
        return action != null && (action.equals("addReview")
                || action.equals("updateReview")
                || action.equals("deleteReview")
                || action.equals("viewReviews")
                || action.equals("editForm"));
    }

    private boolean isFavoriteAction(String action) {
        return action != null && (action.equals("addFavorite")
                || action.equals("removeFavorite")
                || action.equals("viewFavorites"));
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String url = WELCOME_PAGE;

        try {
            // ✅ Nếu là bookForm → redirect kèm tham số
            if ("bookForm".equals(action)) {
                String propertyId = request.getParameter("propertyId");
                String pricePerNight = request.getParameter("pricePerNight");
                String checkIn = request.getParameter("checkIn");
                String checkOut = request.getParameter("checkOut");
                String numGuests = request.getParameter("numGuests");

                String redirectURL = "BookingController?action=bookForm"
                        + "&propertyId=" + propertyId
                        + "&pricePerNight=" + pricePerNight
                        + "&checkIn=" + checkIn
                        + "&checkOut=" + checkOut
                        + "&numGuests=" + numGuests;

                response.sendRedirect(redirectURL);
                return;
            } // ✅ Các action Booking khác (confirmBooking, listUserBookings...) → forward giữ request
            else if (isBookingAction(action)) {
                url = BOOK_CONTROLLER;
            } else if (action == null || action.trim().isEmpty()) {
                List<PolicyDTO> policies = new PolicyDAO().getAllPolicies();
                request.setAttribute("policies", policies);

                List<PropertyDTO> properties = new PropertyDAO().getAllProperties();
                request.setAttribute("properties", properties); // ✅ Đúng tên mà welcome.jsp cần

                url = WELCOME_PAGE;
            } else if (isUserAction(action)) {
                url = USER_CONTROLLER;
            } else if (isPropertyAction(action)) {
                url = PROPERTY_CONTROLLER;
            } else if (isImageAction(action)) {
                url = IMAGE_CONTROLLER;
            } else if (isAvailabilityAction(action)) {
                url = AVAILABILITY_CONTROLLER;
            } else if (isReviewAction(action)) {
                url = REVIEW_CONTROLLER;
            } else if (isFavoriteAction(action)) {
                url = FAVORITE_CONTROLLER;
            } else if (isBookingAction(action)) {
                url = BOOK_CONTROLLER;
            } else if (action.equals("addPolicy") || action.equals("listPolicy")
                    || action.equals("updatePolicy") || action.equals("deletePolicy")) {
                url = POLICY_CONTROLLER;
            } else if ("viewPropertyDetail".equals(action)) {
                int propertyId = Integer.parseInt(request.getParameter("propertyId"));

                PropertyDAO propDAO = new PropertyDAO();
                ReviewDAO revDAO = new ReviewDAO();
                AvailabilityDAO availDAO = new AvailabilityDAO();

                PropertyDTO property = propDAO.getPropertyById(propertyId);
                List<ReviewDTO> reviews = revDAO.getReviewsDTOByPropertyId(propertyId);
                List<LocalDate> unavailableDays = availDAO.getUnavailableDates(propertyId);
                List<java.sql.Date> availableDays = availDAO.getAvailableDatesBetweenTodayAndFuture(propertyId); // ⭐

                request.setAttribute("property", property);
                request.setAttribute("reviews", reviews);
                request.setAttribute("unavailableDates", unavailableDays);
                request.setAttribute("availableDates", availableDays);          // ⭐ gửi sang JSP

                url = "property-detail.jsp";
            } else {
                request.setAttribute("errorMessage", "Hành động không hợp lệ: " + action);
                url = LOGIN_PAGE;
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi hệ thống trong MainController: " + e.getMessage());
            url = LOGIN_PAGE;

        } finally {
            if (!response.isCommitted()) {
                request.getRequestDispatcher(url).forward(request, response);
            }
        }
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
        return "Main Controller định tuyến cho toàn hệ thống";
    }
}
