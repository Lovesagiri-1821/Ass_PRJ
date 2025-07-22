package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.PolicyDAO;
import model.PolicyDTO;
import utils.AuthUtils;

import java.io.IOException;
import java.util.List;

@WebServlet("/PolicyController")
public class PolicyController extends HttpServlet {

    private final PolicyDAO dao = new PolicyDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("view".equals(action)) {
                // Ai cũng xem được
                handleView(request, response);
                return;
            }

           // Các action khác chỉ cho admin hoặc host
if (!AuthUtils.isAdmin(request) && !AuthUtils.isHost(request)) {
    request.setAttribute("message", AuthUtils.getAccessDeniedMessage("quản lý Policy"));
    handleView(request, response);
    return;
}


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
                default:
                    request.setAttribute("message", "Hành động không hợp lệ!");
                    handleList(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Lỗi: " + e.getMessage());
            handleList(request, response);
        }
    }

    /** Trang view cho tất cả */
    private void handleView(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<PolicyDTO> list = dao.getAllPolicies();
        request.setAttribute("policies", list);
        request.getRequestDispatcher("policy-view.jsp").forward(request, response);
    }

    /** Trang quản lý cho admin */
    private void handleList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<PolicyDTO> list = dao.getAllPolicies();
        request.setAttribute("policies", list);
        request.getRequestDispatcher("policy-list.jsp").forward(request, response);
    }

    private void handleAdd(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String title = request.getParameter("title");
        String desc = request.getParameter("description");
        dao.addPolicy(new PolicyDTO(0, title, desc));
        response.sendRedirect("PolicyController?action=list");
    }

    private void handleUpdate(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("policyId"));
        String title = request.getParameter("title");
        String desc = request.getParameter("description");
        dao.updatePolicy(new PolicyDTO(id, title, desc));
        response.sendRedirect("PolicyController?action=list");
    }

    private void handleDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("policyId"));
        dao.deletePolicy(id);
        response.sendRedirect("PolicyController?action=list");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
    }
}
