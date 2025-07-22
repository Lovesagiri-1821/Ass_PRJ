package controller;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.AvailabilityDAO;
import model.AvailabilityDTO;
import utils.AuthUtils;

@WebServlet(name = "AvailabilityController", urlPatterns = {"/AvailabilityController"})
public class AvailabilityController extends HttpServlet {

    private static final String ERROR_PAGE = "error.jsp";
    private static final String AVAILABILITY_LIST_PAGE = "availability-list.jsp"; // tạo JSP này sau nếu cần

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        AvailabilityDAO dao = new AvailabilityDAO();

        try {
            switch (action) {
                case "addAvailability":
                    if (!AuthUtils.isHostOrAdmin(request)) {
                        denyAccess(request, response, "thêm ngày trống");
                        return;
                    }
                    addAvailability(request, response, dao);
                    break;

                case "updateAvailability":
                    if (!AuthUtils.isHostOrAdmin(request)) {
                        denyAccess(request, response, "cập nhật trạng thái ngày");
                        return;
                    }
                    updateAvailability(request, response, dao);
                    break;

                case "setPrice":
                    if (!AuthUtils.isHostOrAdmin(request)) {
                        denyAccess(request, response, "cập nhật giá");
                        return;
                    }
                    setPrice(request, response, dao);
                    break;

                case "viewAvailability":
                    viewAvailability(request, response, dao);
                    break;

                default:
                    request.setAttribute("errorMessage", "Hành động không hợp lệ: " + action);
                    request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi trong AvailabilityController: " + e.getMessage());
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }
    }

    // ✅ Hiển thị thông báo khi không đủ quyền truy cập
    private void denyAccess(HttpServletRequest request, HttpServletResponse response, String action)
            throws ServletException, IOException {
        request.setAttribute("message", AuthUtils.getAccessDeniedMessage(action));
        request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
    }

    private void addAvailability(HttpServletRequest request, HttpServletResponse response, AvailabilityDAO dao)
            throws ServletException, IOException {
        try {
            int propertyId = Integer.parseInt(request.getParameter("propertyId"));
            Date date = Date.valueOf(request.getParameter("date"));
            double price = Double.parseDouble(request.getParameter("price"));

            boolean success = dao.insertAvailability(request, propertyId, date, price);

            if (success) {
                request.setAttribute("message", "Thêm ngày thành công!");
            } else {
                request.setAttribute("message", "Không thể thêm ngày (đã tồn tại hoặc không có quyền)!");
            }

            viewAvailability(request, response, dao);
        } catch (Exception e) {
            throw new ServletException("Lỗi khi thêm ngày availability", e);
        }
    }

    private void updateAvailability(HttpServletRequest request, HttpServletResponse response, AvailabilityDAO dao)
            throws ServletException, IOException {
        try {
            int propertyId = Integer.parseInt(request.getParameter("propertyId"));
            Date date = Date.valueOf(request.getParameter("date"));
            boolean isAvailable = Boolean.parseBoolean(request.getParameter("isAvailable"));

            boolean success = dao.updateAvailability(request, propertyId, date, isAvailable);

            if (success) {
                request.setAttribute("message", "Cập nhật trạng thái thành công!");
            } else {
                request.setAttribute("message", "Không thể cập nhật trạng thái!");
            }

            viewAvailability(request, response, dao);
        } catch (Exception e) {
            throw new ServletException("Lỗi khi cập nhật availability", e);
        }
    }

    private void setPrice(HttpServletRequest request, HttpServletResponse response, AvailabilityDAO dao)
            throws ServletException, IOException {
        try {
            int propertyId = Integer.parseInt(request.getParameter("propertyId"));
            Date date = Date.valueOf(request.getParameter("date"));
            double price = Double.parseDouble(request.getParameter("price"));

            boolean success = dao.setPrice(request, propertyId, date, price);

            if (success) {
                request.setAttribute("message", "Cập nhật giá thành công!");
            } else {
                request.setAttribute("message", "Không thể cập nhật giá!");
            }

            viewAvailability(request, response, dao);
        } catch (Exception e) {
            throw new ServletException("Lỗi khi cập nhật giá", e);
        }
    }

    private void viewAvailability(HttpServletRequest request, HttpServletResponse response, AvailabilityDAO dao)
            throws ServletException, IOException {
        int propertyId = Integer.parseInt(request.getParameter("propertyId"));

        List<AvailabilityDTO> list = dao.getAvailabilityByProperty(request, propertyId);

        request.setAttribute("availabilityList", list);
        request.setAttribute("propertyId", propertyId);
        request.getRequestDispatcher(AVAILABILITY_LIST_PAGE).forward(request, response);
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
        return "Controller xử lý các hành động của bảng Availability";
    }
}
