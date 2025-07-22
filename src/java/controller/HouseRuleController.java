package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.HouseRuleDAO;
import model.HouseRuleDTO;
import utils.AuthUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/HouseRuleController")
public class HouseRuleController extends HttpServlet {
    private final HouseRuleDAO dao = new HouseRuleDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            switch (action) {
                case "list":
                    handleList(request, response);
                    break;
                case "add":
                    handleAdd(request, response);
                    break;
                case "update":
                    handleUpdate(request, response);
                    break;
                case "delete":
                    handleDelete(request, response);
                    break;
               
   case "addForm":
                    showAddForm(request, response);
                    break;
                case "editForm":
                    showEditForm(request, response);
                    break;

                default:
                    request.setAttribute("message", "Hành động không hợp lệ!");
                    request.getRequestDispatcher("houseRule-list.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Lỗi: " + e.getMessage());
            request.getRequestDispatcher("houseRule-list.jsp").forward(request, response);
        }
    }

    private void handleList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<HouseRuleDTO> list;
        if (AuthUtils.isAdmin(request)) {
            list = dao.getAllHouseRules();
        } else if (AuthUtils.isHost(request)) {
            int hostId = AuthUtils.getCurrentUser(request).getUserID();
            list = dao.getHouseRulesByHost(hostId);  // cần viết hàm này
        } else {
            request.setAttribute("message", AuthUtils.getAccessDeniedMessage("xem House Rules"));
            list = new ArrayList<>();

        }
        request.setAttribute("houseRuleList", list);
        request.getRequestDispatcher("booking.jsp").forward(request, response);
    }

   private void handleAdd(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    if (AuthUtils.isAdmin(request) || AuthUtils.isHost(request)) {
        int propertyId = Integer.parseInt(request.getParameter("propertyId"));
        String ruleText = request.getParameter("ruleText");
        dao.addHouseRule(new HouseRuleDTO(0, propertyId, ruleText));
        
        // ✅ Sau khi thêm xong, redirect về booking.jsp của property đó
        response.sendRedirect("BookingController?action=showBooking&propertyId=" + propertyId);
    } else {
        request.setAttribute("message", AuthUtils.getAccessDeniedMessage("thêm House Rule"));
        request.getRequestDispatcher("houseRule-list.jsp").forward(request, response);
    }
}

    private void handleUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    if (AuthUtils.isAdmin(request) || AuthUtils.isHost(request)) {
        int ruleId = Integer.parseInt(request.getParameter("ruleId"));
        String ruleText = request.getParameter("ruleText");
        int propertyId = Integer.parseInt(request.getParameter("propertyId"));  // thêm dòng này

        dao.updateHouseRule(new HouseRuleDTO(ruleId, 0, ruleText));
        
        // ✅ Redirect về booking.jsp
        response.sendRedirect("BookingController?action=showBooking&propertyId=" + propertyId);
    } else {
        request.setAttribute("message", AuthUtils.getAccessDeniedMessage("cập nhật House Rule"));
        request.getRequestDispatcher("houseRule-list.jsp").forward(request, response);
    }
}


    private void handleDelete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    if (AuthUtils.isAdmin(request) || AuthUtils.isHost(request)) {
        int ruleId = Integer.parseInt(request.getParameter("ruleId"));
        int propertyId = Integer.parseInt(request.getParameter("propertyId"));  // thêm dòng này

        dao.deleteHouseRule(ruleId);
        
        // ✅ Redirect về booking.jsp
        response.sendRedirect("BookingController?action=showBooking&propertyId=" + propertyId);
    } else {
        request.setAttribute("message", AuthUtils.getAccessDeniedMessage("xóa House Rule"));
        request.getRequestDispatcher("houseRule-list.jsp").forward(request, response);
    }
}


    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }
    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }
private void showAddForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int propertyId = Integer.parseInt(request.getParameter("propertyId"));
        request.setAttribute("propertyId", propertyId);
        request.getRequestDispatcher("addHouseRule.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int ruleId = Integer.parseInt(request.getParameter("ruleId"));
        int propertyId = Integer.parseInt(request.getParameter("propertyId"));
        HouseRuleDTO rule = dao.getHouseRuleById(ruleId); // cần viết trong DAO
        request.setAttribute("rule", rule);
        request.setAttribute("propertyId", propertyId);
        request.getRequestDispatcher("editHouseRule.jsp").forward(request, response);
    }
    
}
