<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Image List</title>
        <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@300;500;700&display=swap" rel="stylesheet">
        <style>
            body {
                margin: 0;
                font-family: 'Outfit', sans-serif;
                background-color: #fff;
                color: #333;
                display: flex;
                flex-direction: column;
                min-height: 100vh;
            }

            header, footer {
                background-color: #f8f0fa;
                padding: 15px 30px;
                text-align: center;
                font-weight: bold;
                color: #670D2F;
            }

            .main-content {
                flex: 1;
                padding: 40px 20px;
                max-width: 1000px;
                margin: auto;
            }

            h2 {
                color: #A53860;
                text-align: center;
                margin-bottom: 20px;
            }

            .add-button {
                display: inline-block;
                margin-bottom: 20px;
                background: #A53860;
                color: #fff;
                padding: 10px 16px;
                border-radius: 8px;
                text-decoration: none;
                font-weight: bold;
                transition: background 0.3s, transform 0.2s;
            }

            .add-button:hover {
                background: #EF88AD;
                transform: scale(1.03);
            }

            .image-grid {
                display: flex;
                overflow-x: auto;
                gap: 15px;
                padding-bottom: 10px;
                scroll-snap-type: x mandatory; /* cuộn mượt theo ảnh */
            }


            .image-item {
                flex: 0 0 auto; /* giữ kích thước cố định, không co lại */
                width: 300px;
                border: 1px solid #f0d9e8;
                padding: 8px;
                border-radius: 8px;
                background: #fdfbff;
                text-align: center;
                box-shadow: 0 4px 12px rgba(58, 5, 105, 0.08);
                transition: transform 0.3s ease;
                scroll-snap-align: start; /* dính ảnh khi cuộn */
            }

            .image-item img {
                width: 100%;
                height: 180px;
                object-fit: cover;
                border-radius: 6px;
                margin-bottom: 8px;
            }


            .image-item:hover {
                transform: translateY(-4px);
            }

            .image-item img {
                max-width: 100%;
                border-radius: 6px;
                margin-bottom: 8px;
            }

            .actions a {
                margin: 0 4px;
                text-decoration: none;
                color: #670D2F;
                font-size: 14px;
                font-weight: bold;
                transition: color 0.3s;
            }

            .actions a:hover {
                color: #A53860;
            }

            .back-link {
                display: inline-block;
                margin-top: 20px;
                color: #670D2F;
                text-decoration: none;
                font-weight: bold;
                transition: color 0.3s;
            }

            .back-link:hover {
                color: #A53860;
            }

            .no-image {
                color: #999;
                text-align: center;
                margin-top: 20px;
            }
        </style>
    </head>
    <body>

        <header>
            <div style="display: flex; justify-content: space-between; align-items: center;">
                <div style="flex: 1; display: flex; justify-content: flex-start;">
                    <img src="images/header.jpg" alt="Logo" style="width: 80px; height: 80px; border-radius: 50%; padding: 10px; margin-left: 30px;">
                </div>
                <div style="flex: 2; text-align: center;">
                    <span style="font-weight: bold; color: #670D2F; font-size: 18px;">
                        🌸 Welcome to Haus Thurni 🌸
                    </span>
                </div>
                <div style="flex: 1;"></div>
            </div>
        </header>

        <div class="main-content">
            <h2>🖼️ Image list for: ${property.name}</h2>

            <c:if test="${sessionScope.user != null && (sessionScope.user.role == 'admin' || sessionScope.user.role == 'host')}">
                <a class="add-button" href="ImageController?action=addForm&propertyId=${property.propertyId}">➕ Add new image</a>
            </c:if>

            <c:if test="${not empty images}">
                <div class="image-grid">
                    <c:forEach var="img" items="${images}">
                        <div class="image-item">
                            <img src="${img.imageUrl}" alt="Image">
                            <c:if test="${sessionScope.user != null && (sessionScope.user.role == 'admin' || sessionScope.user.role == 'host')}">
                                <div class="actions">
                                    <a href="ImageController?action=editForm&imageId=${img.imageId}">✏️ Edit</a> |
                                    <a href="ImageController?action=delete&imageId=${img.imageId}&propertyId=${property.propertyId}" onclick="return confirm('Delete this image?');">🗑️ Delete</a>
                                </div>
                            </c:if>
                        </div>
                    </c:forEach>
                </div>
            </c:if>

            <c:if test="${empty images}">
                <p class="no-image">No images available.</p>
            </c:if>

            <a class="back-link" href="MainController?action=viewPropertyDetail&propertyId=${property.propertyId}">⬅️ Back to property details</a>
        </div>

        <footer>
            © 2025 Booking System | Designed by Nhi&Thuong ✨
        </footer>

    </body>
</html>
