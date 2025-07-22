package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import model.ImageDAO;
import model.ImageDTO;
import model.PropertyDAO;
import model.PropertyDTO;

@WebServlet(name = "ImageController", urlPatterns = {"/ImageController"})
public class ImageController extends HttpServlet {

    private final ImageDAO imageDAO = new ImageDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if (action == null) {
                response.sendRedirect("error.jsp");
                return;
            }
            switch (action) {
                case "view":
                    viewImagesByPropertyId(request, response);
                    break;
                case "addForm":
                    showAddForm(request, response);
                    break;
                case "editForm":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteImage(request, response);
                    break;
                default:
                    response.sendRedirect("error.jsp");
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if (action == null) {
                response.sendRedirect("error.jsp");
                return;
            }
            switch (action) {
                case "add":
                    addImage(request, response);
                    break;
                case "update":
                    updateImage(request, response);
                    break;
                default:
                    response.sendRedirect("error.jsp");
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    private void viewImagesByPropertyId(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, SQLException {
    int propertyId = Integer.parseInt(request.getParameter("propertyId"));
    List<ImageDTO> images = imageDAO.getImagesDTOByPropertyId(propertyId);

    // Lấy property để hiển thị tên
    PropertyDAO propertyDAO = new PropertyDAO();
    PropertyDTO property = propertyDAO.getPropertyById(propertyId);

    request.setAttribute("images", images);
    request.setAttribute("property", property); // thêm dòng này
    request.getRequestDispatcher("imageList.jsp").forward(request, response);
}


    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int propertyId = Integer.parseInt(request.getParameter("propertyId"));
        request.setAttribute("propertyId", propertyId);
        request.getRequestDispatcher("addImage.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int imageId = Integer.parseInt(request.getParameter("imageId"));
        ImageDTO image = imageDAO.getImageDTOById(imageId);
        request.setAttribute("image", image);
        request.getRequestDispatcher("editImage.jsp").forward(request, response);
    }

    private void addImage(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, SQLException {
    int propertyId = Integer.parseInt(request.getParameter("propertyId"));
    String imageUrl = request.getParameter("imageUrl");

    // Lấy sortOrder: bạn có thể để mặc định = 0 hoặc tự tính
    int sortOrder = 0;

    // Thêm ảnh mới
    ImageDTO newImage = new ImageDTO(propertyId, imageUrl, sortOrder);
    imageDAO.addImageDTO(newImage);

 // Thêm flash message
    request.getSession().setAttribute("flashMessage", "✔️ Ảnh đã được thêm thành công!");

    // Redirect về danh sách ảnh
    response.sendRedirect("ImageController?action=view&propertyId=" + propertyId);

}


    private void updateImage(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int imageId = Integer.parseInt(request.getParameter("imageId"));
        int propertyId = Integer.parseInt(request.getParameter("propertyId"));
        String imageUrl = request.getParameter("imageUrl");
        int sortOrder = Integer.parseInt(request.getParameter("sortOrder"));
        ImageDTO imageDTO = new ImageDTO(imageId, propertyId, imageUrl, sortOrder);
        imageDAO.updateImageDTO(imageDTO);
        response.sendRedirect("ImageController?action=view&propertyId=" + propertyId);
    }

    private void deleteImage(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int imageId = Integer.parseInt(request.getParameter("imageId"));
        int propertyId = Integer.parseInt(request.getParameter("propertyId"));
        imageDAO.deleteImageDTO(imageId);
        response.sendRedirect("ImageController?action=view&propertyId=" + propertyId);
    }
}
