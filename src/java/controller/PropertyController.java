// ✅ ĐÃ SỬA HOÀN CHỈNH PropertyController.java
// - Thêm response trong handleAdd và handleUpdate
// - Sửa lỗi không redirect khi thêm property
package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.time.LocalDate;
import model.PropertyDAO;
import model.PropertyDTO;
import model.ReviewDAO;
import model.ReviewDTO;
import model.UserDTO;
import utils.DbUtils;
import java.util.stream.Collectors;
import model.AvailabilityDAO;

@WebServlet(name = "PropertyController", urlPatterns = {"/PropertyController"})
public class PropertyController extends HttpServlet {

    private static final String property_list_page = "property-list.jsp";
    private static final String property_edit_page = "property-edit.jsp";
    private static final String property_add_page = "property-add.jsp";
    private static final String error_page = "error.jsp";
    private static final String PROPERTY_DETAIL_PAGE = "property-detail.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String url = error_page;

        try ( Connection conn = DbUtils.getConnection()) {
            PropertyDAO dao = new PropertyDAO(conn);
            String action = request.getParameter("action");

            switch (action) {
                case "propertyList":
                    url = handleList(request, dao);
                    break;
                case "propertySearch":
                    url = handleSearch(request, dao);
                    break;
                case "propertyAdd":
                    url = handleAdd(request, response, dao); // ✅ thêm response
                    return; // ⚠️ tránh forward sau sendRedirect
                case "propertyEdit":
                    url = handleEditForm(request, dao);
                    break;
                case "propertyUpdate":
                    url = handleUpdate(request, response, dao); // ✅ thêm response
                    return;
                case "propertyDelete":
                    url = handleDelete(request, dao);
                    break;
                case "propertyFilter":
                    url = handleFilter(request, dao);
                    break;
                case "propertyDetail":
                    url = handleDetail(request, dao);
                    break;
                case "propertyHostList":
                    url = handleHostList(request, dao);
                    break;

                default:
                    request.setAttribute("message", "Invalid action: " + action);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "System error in PropertyController: " + e.getMessage());
        }

        request.getRequestDispatcher(url).forward(request, response);
    }

    private String handleList(HttpServletRequest request, PropertyDAO dao) throws SQLException {
        List<PropertyDTO> list = dao.getAllProperties();
        request.setAttribute("properties", list);
        return property_list_page;
    }

    private String handleSearch(HttpServletRequest request, PropertyDAO dao) throws SQLException {
        String keyword = request.getParameter("keyword");
        int guests = 0;
        try {
            guests = Integer.parseInt(request.getParameter("guests"));
        } catch (Exception e) {
            guests = 0;
        }
        List<PropertyDTO> results = dao.search(keyword, guests);
        request.setAttribute("properties", results);
        return property_list_page;
    }

    private String handleAdd(HttpServletRequest request, HttpServletResponse response, PropertyDAO dao) throws SQLException, IOException {
        HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute("user");

        if (user == null || (!"admin".equalsIgnoreCase(user.getRole()) && !"host".equalsIgnoreCase(user.getRole()))) {
            request.setAttribute("message", "Bạn không có quyền thêm property.");
            return error_page;
        }

        PropertyDTO property = extractPropertyFromRequest(request, user.getUserID());
        dao.addProperty(property);
        response.sendRedirect("PropertyController?action=propertyList");
        return null;
    }

    private String handleUpdate(HttpServletRequest request, HttpServletResponse response, PropertyDAO dao)
            throws SQLException, IOException {

        HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute("user");

        if (user == null) {
            request.setAttribute("message", "Bạn chưa đăng nhập.");
            return "error.jsp";
        }

        int id = Integer.parseInt(request.getParameter("id"));
        PropertyDTO oldProperty = dao.getPropertyById(id);

        if (oldProperty == null) {
            request.setAttribute("message", "Property không tồn tại.");
            return "error.jsp";
        }

        // ✅ Chỉ admin hoặc host chính chủ mới được update
        if ("admin".equalsIgnoreCase(user.getRole())
                || ("host".equalsIgnoreCase(user.getRole()) && oldProperty.getHostId() == user.getUserID())) {

            PropertyDTO property = extractPropertyFromRequest(request, user.getUserID());
            property.setPropertyId(id);
            dao.updateProperty(property);
            response.sendRedirect("PropertyController?action=propertyList");
            return null;
        }

        request.setAttribute("message", "Bạn không có quyền cập nhật property này.");
        return "error.jsp";
    }

    private String handleEditForm(HttpServletRequest request, PropertyDAO dao) throws SQLException {
        HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute("user");

        if (user == null) {
            request.setAttribute("message", "Bạn chưa đăng nhập.");
            return "error.jsp";
        }

        int id = Integer.parseInt(request.getParameter("id"));
        PropertyDTO property = dao.getPropertyById(id);

        if (property == null) {
            request.setAttribute("message", "Property không tồn tại.");
            return "error.jsp";
        }

        // ✅ Admin hoặc Host (chính chủ) mới được sửa
        if ("admin".equalsIgnoreCase(user.getRole())
                || ("host".equalsIgnoreCase(user.getRole()) && property.getHostId() == user.getUserID())) {
            request.setAttribute("property", property);
            return "property-edit.jsp";
        }

        request.setAttribute("message", "Bạn không có quyền sửa property này.");
        return "error.jsp";
    }

    private String handleDelete(HttpServletRequest request, PropertyDAO dao) throws SQLException {
        HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute("user");
        int id = Integer.parseInt(request.getParameter("id"));
        PropertyDTO p = dao.getPropertyById(id);

        if (user == null) {
            request.setAttribute("message", "Bạn chưa đăng nhập.");
            return "error.jsp";
        }

        // ✅ Admin được xoá bất kỳ property
        if ("admin".equalsIgnoreCase(user.getRole())) {
            dao.deleteProperty(id);
            return handleList(request, dao);
        }

        // ✅ Host chỉ được xoá property của chính mình
        if ("host".equalsIgnoreCase(user.getRole()) && p.getHostId() == user.getUserID()) {
            dao.deleteProperty(id);
            return handleList(request, dao);
        }

        // ❌ Không đủ quyền
        request.setAttribute("message", "Bạn không có quyền xoá property này.");
        return "error.jsp";
    }

    private String handleFilter(HttpServletRequest request, PropertyDAO dao) throws SQLException {
        List<PropertyDTO> all = dao.getAllProperties();
        request.setAttribute("properties", all);
        return property_list_page;
    }

//    private String handleDetail(HttpServletRequest request, PropertyDAO dao) throws SQLException {
//        int id = Integer.parseInt(request.getParameter("id"));
//        PropertyDTO property = dao.getPropertyById(id);
//        request.setAttribute("property", property);
//        return "property-detail.jsp";
//    }
    private String handleDetail(HttpServletRequest request, PropertyDAO dao) throws SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        PropertyDTO property = dao.getPropertyById(id);

        ReviewDAO reviewDAO = new ReviewDAO();
        List<ReviewDTO> reviews = reviewDAO.getReviewsDTOByPropertyId(id);

        AvailabilityDAO availabilityDAO = new AvailabilityDAO();
        List<LocalDate> unavailableDates = availabilityDAO.getUnavailableDates(id);
        List<String> unavailableDateStrings = unavailableDates.stream()
                .map(LocalDate::toString)
                .collect(Collectors.toList());

        request.setAttribute("property", property);
        request.setAttribute("reviews", reviews);
        request.setAttribute("unavailableDates", unavailableDateStrings);
        return PROPERTY_DETAIL_PAGE;
    }

    private PropertyDTO extractPropertyFromRequest(HttpServletRequest request, int hostId) {
        PropertyDTO p = new PropertyDTO();
        p.setName(request.getParameter("name"));
        p.setLocationId(Integer.parseInt(request.getParameter("location_id")));
        p.setHostId(hostId);
        p.setPricePerNight(new java.math.BigDecimal(request.getParameter("price_per_night")));
        p.setDescription(request.getParameter("description"));
        p.setNumBedrooms(Integer.parseInt(request.getParameter("num_bedrooms")));
        p.setNumBathrooms(Integer.parseInt(request.getParameter("num_bathrooms")));
        p.setNumGuests(Integer.parseInt(request.getParameter("num_guests")));
        p.setNumRooms(Integer.parseInt(request.getParameter("num_rooms")));
        p.setHasWifi(request.getParameter("has_wifi") != null);
        p.setAllowsPets(request.getParameter("allows_pets") != null);
        p.setHasBalcony(request.getParameter("has_balcony") != null);
        p.setHasParking(request.getParameter("has_parking") != null);
        p.setHasPrivatePool(request.getParameter("has_private_pool") != null);
        p.setHasEvStation(request.getParameter("has_ev_station") != null);
        p.setAllowsSmoking(request.getParameter("allows_smoking") != null);
        p.setNearBeach(request.getParameter("near_beach") != null);
        p.setNearLake(request.getParameter("near_lake") != null);
        p.setNearRiver(request.getParameter("near_river") != null);
        p.setNearCountryside(request.getParameter("near_countryside") != null);
        p.setNearCityCenter(request.getParameter("near_city_center") != null);
        p.setDistanceToBeach(new java.math.BigDecimal(request.getParameter("distance_to_beach")));
        p.setDistanceToLake(new java.math.BigDecimal(request.getParameter("distance_to_lake")));
        p.setDistanceToCityCenter(new java.math.BigDecimal(request.getParameter("distance_to_city_center")));
        p.setThumbnailUrl(request.getParameter("thumbnail_url"));
        p.setShortDescription(request.getParameter("short_description"));
        p.setStatus("active");
        return p;
    }

    private String handleHostList(HttpServletRequest request, PropertyDAO dao) throws SQLException {
        HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute("user");

        if (user == null || !"host".equalsIgnoreCase(user.getRole())) {
            request.setAttribute("message", "Bạn không có quyền truy cập danh sách này.");
            return "error.jsp";
        }

        List<PropertyDTO> hostProperties = dao.getPropertiesByHost(user.getUserID());

        System.out.println("===> hostID = " + user.getUserID());
        System.out.println("===> hostProperties size = " + hostProperties.size());

        request.setAttribute("properties", hostProperties);
        return property_list_page; // dùng chung trang property-list.jsp
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

}
