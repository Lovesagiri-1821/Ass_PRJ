package controller;

import model.BookingDAO;
import model.BookingDTO;
import model.PropertyDAO;
import model.PropertyDTO;
import model.UserDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.time.format.DateTimeFormatter;
import model.HouseRuleDAO;
import model.HouseRuleDTO;

@WebServlet(name = "BookingController", urlPatterns = {"/BookingController"})
public class BookingController extends HttpServlet {

    private BookingDAO bookingDAO;

    @Override
    public void init() {
        bookingDAO = new BookingDAO();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("bookForm".equals(action)) {
                handleBookingForm(request, response);
            } else if ("listUserBookings".equals(action)) {
                handleListUserBookings(request, response);
            } else if ("confirmBooking".equals(action)) {
                handleConfirmBooking(request, response);
            } else if ("confirmPayment".equals(action)) {
                handleConfirmPayment(request, response);
            } else if ("cancelBooking".equals(action)) {
                handleCancelBooking(request, response);
            } else {
                response.sendRedirect("MainController?action=propertyList");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorOccurred", true);
            request.getRequestDispatcher("booking.jsp").forward(request, response);

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

    private void handleBookingForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String propIdStr = request.getParameter("propertyId");
            String priceStr = request.getParameter("pricePerNight");
            String checkInStr = request.getParameter("checkIn");
            String checkOutStr = request.getParameter("checkOut");
            String guestsStr = request.getParameter("numGuests");

            if (propIdStr == null || priceStr == null || checkInStr == null
                    || checkOutStr == null || guestsStr == null
                    || propIdStr.isEmpty() || priceStr.isEmpty() || checkInStr.isEmpty()
                    || checkOutStr.isEmpty() || guestsStr.isEmpty()) {
                throw new IllegalArgumentException("Thiếu tham số!");
            }

            int propertyId = Integer.parseInt(propIdStr);
            double pricePerNight = Double.parseDouble(priceStr);
            LocalDate checkIn = LocalDate.parse(checkInStr);
            LocalDate checkOut = LocalDate.parse(checkOutStr);
            int numGuests = Integer.parseInt(guestsStr);

            long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
            if (nights <= 0) {
                throw new IllegalArgumentException("Ngày check-out phải sau ngày check-in.");
            }

            double totalPrice = nights * pricePerNight;

            PropertyDAO propertyDAO = new PropertyDAO();
            PropertyDTO property = propertyDAO.getPropertyById(propertyId);
            if (property == null) {
                throw new Exception("Không tìm thấy property với ID: " + propertyId);
            }

            HouseRuleDAO ruleDAO = new HouseRuleDAO();
            List<HouseRuleDTO> houseRules = ruleDAO.getHouseRulesByProperty(propertyId);

            request.setAttribute("property", property);
            request.setAttribute("houseRules", houseRules);
            request.setAttribute("checkIn", checkIn);
            request.setAttribute("checkOut", checkOut);
            request.setAttribute("numGuests", numGuests);
            request.setAttribute("totalPrice", totalPrice);
            request.setAttribute("pricePerNight", pricePerNight);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String checkInFormatted = checkIn.format(formatter);
            String checkOutFormatted = checkOut.format(formatter);
            request.setAttribute("checkInFormatted", checkInFormatted);
            request.setAttribute("checkOutFormatted", checkOutFormatted);
            request.setAttribute("checkIn", checkIn.toString());
            request.setAttribute("checkOut", checkOut.toString());

            request.getRequestDispatcher("booking.jsp").forward(request, response);

            System.out.println("DEBUG: houseRules.size = " + (houseRules != null ? houseRules.size() : 0));
            for (HouseRuleDTO rule : houseRules) {
                System.out.println(" - Rule: " + rule.getRuleText());
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi khi hiển thị form đặt phòng: " + e.getMessage());
            request.getRequestDispatcher("booking.jsp").forward(request, response);

        }
    }

    private void handleListUserBookings(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        UserDTO user = (session != null) ? (UserDTO) session.getAttribute("user") : null;

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            List<BookingDTO> bookings = bookingDAO.getBookingsByUserId(user.getUserID());
            request.setAttribute("bookings", bookings);
            request.getRequestDispatcher("userBookings.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi khi lấy danh sách đặt phòng: " + e.getMessage());
            request.getRequestDispatcher("booking.jsp").forward(request, response);
        }
    }

    private void handleConfirmBooking(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("DEBUG: Vào handleConfirmBooking");  // 🐞 debug

        HttpSession session = request.getSession(false);
        UserDTO user = (session != null) ? (UserDTO) session.getAttribute("user") : null;

        if (user == null) {
            System.out.println("DEBUG: User chưa đăng nhập -> redirect login.jsp");
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            // Lấy tham số từ request
            String propertyIdStr = request.getParameter("propertyId");
            String pricePerNightStr = request.getParameter("pricePerNight");
            String checkInStr = request.getParameter("checkIn");
            String checkOutStr = request.getParameter("checkOut");
            String numGuestsStr = request.getParameter("numGuests");

            // In debug giá trị nhận được
            System.out.println("DEBUG: propertyId=" + propertyIdStr);
            System.out.println("DEBUG: pricePerNight=" + pricePerNightStr);
            System.out.println("DEBUG: checkIn=" + checkInStr);
            System.out.println("DEBUG: checkOut=" + checkOutStr);
            System.out.println("DEBUG: numGuests=" + numGuestsStr);

            // Parse dữ liệu
            int propertyId = Integer.parseInt(propertyIdStr);
            double pricePerNight = Double.parseDouble(pricePerNightStr);
            LocalDate checkIn = LocalDate.parse(checkInStr);
            LocalDate checkOut = LocalDate.parse(checkOutStr);
            int numGuests = Integer.parseInt(numGuestsStr);






            long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
            System.out.println("DEBUG: nights=" + nights);

            double totalPrice = nights * pricePerNight;
            System.out.println("DEBUG: totalPrice=" + totalPrice);

            BookingDTO booking = new BookingDTO();
            booking.setUserId(user.getUserID());
            booking.setPropertyId(propertyId);
            booking.setCheckIn(checkIn);
            booking.setCheckOut(checkOut);
            booking.setNumberOfGuests(numGuests);
            booking.setTotalPrice(totalPrice);
            booking.setStatus("booked");
            booking.setBookingDateTime(LocalDateTime.now());
            booking.setRefundAmount(0.0);

            int bookingId = bookingDAO.createBooking(booking);
            System.out.println("DEBUG: bookingId=" + bookingId);

// Kiểm tra bookingId để biết thành công hay thất bại
            if (bookingId > 0) {
                System.out.println("DEBUG: Đặt phòng thành công");

                // ✅ Gọi DAO tạo bản ghi thanh toán pending
                try {
                    model.PaymentDTO payment = new model.PaymentDTO();
                    payment.setBookingId(bookingId);
                    payment.setMethod("qr_manual"); // hoặc "momo", "bank_transfer"
                    payment.setAmount(java.math.BigDecimal.valueOf(totalPrice));
                    payment.setStatus("pending");
                    payment.setPaymentDatetime(java.sql.Timestamp.valueOf(LocalDateTime.now()));

                    model.PaymentDAO paymentDAO = new model.PaymentDAO();
                    paymentDAO.createPayment(payment);

                    System.out.println("DEBUG: Đã tạo bản ghi Payment thành công");
                } catch (Exception ex) {
                    System.err.println("Lỗi khi tạo Payment: " + ex.getMessage());
                }

                // ✅ Chuyển sang trang thanh toán mã QR
                response.sendRedirect("payment.jsp?bookingId=" + bookingId + "&amount=" + totalPrice);
                return;
            } else {
                System.out.println("DEBUG: Đặt phòng thất bại");
                request.setAttribute("bookingSuccess", false);
            }

            // Lấy lại property để hiện trên trang
            PropertyDAO propertyDAO = new PropertyDAO();
            PropertyDTO property = propertyDAO.getPropertyById(propertyId);
            request.setAttribute("property", property);

            HouseRuleDAO ruleDAO = new HouseRuleDAO();
            List<HouseRuleDTO> houseRules = ruleDAO.getHouseRulesByProperty(propertyId);
            request.setAttribute("houseRules", houseRules);

            request.setAttribute("checkInFormatted", checkIn.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            request.setAttribute("checkOutFormatted", checkOut.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            request.setAttribute("numGuests", numGuests);
            request.setAttribute("pricePerNight", pricePerNight);
            request.setAttribute("totalPrice", totalPrice);
            request.setAttribute("checkIn", checkIn.toString());
            request.setAttribute("checkOut", checkOut.toString());

            // Chuyển về booking.jsp
            request.getRequestDispatcher("booking.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi khi xác nhận đặt phòng: " + e.getMessage());
            request.getRequestDispatcher("booking.jsp").forward(request, response);
        }
    }

    private void handleConfirmPayment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String bookingIdStr = request.getParameter("bookingId");

        try {
            int bookingId = Integer.parseInt(bookingIdStr);

            model.PaymentDAO paymentDAO = new model.PaymentDAO();
            boolean updated = paymentDAO.updatePaymentStatus(bookingId, "success");

            if (updated) {
                request.setAttribute("paymentSuccess", true);
            } else {
                request.setAttribute("paymentSuccess", false);
            }

            request.getRequestDispatcher("payment-result.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi xác nhận thanh toán: " + e.getMessage());
            request.getRequestDispatcher("payment-result.jsp").forward(request, response);
        }
    }

    /**
     * Huỷ booking và quay lại trang chi tiết property
     */
    /**
     * Huỷ booking (nếu có) rồi quay về trang welcome.jsp
     */
    private void handleCancelBooking(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // 1️⃣ lấy bookingId (có thể null nếu user chưa tạo booking)
            String bookingIdStr = request.getParameter("bookingId");
            if (bookingIdStr != null && !bookingIdStr.trim().isEmpty()) {
                int bookingId = Integer.parseInt(bookingIdStr);
                bookingDAO.cancelBooking(bookingId);     // huỷ, không cần quan tâm kết quả
                System.out.println("DEBUG: cancel bookingId = " + bookingId);
            }
        } catch (Exception ex) {
            // log nhưng vẫn quay về welcome
            ex.printStackTrace();
        }

        // 2️⃣ luôn quay về danh sách property
        response.sendRedirect("MainController?action=propertyList");
    }

    @Override
    public String getServletInfo() {
        return "BookingController quản lý đặt phòng";
    }
}
