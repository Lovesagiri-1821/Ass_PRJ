<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Property List</title>
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
                padding: 40px 60px;
                width: 100%;
                max-width: unset;
                box-sizing: border-box;
            }

            h2 {
                text-align: center;
                color: #A53860;
                margin-bottom: 20px;
            }

            .add-btn {
                display: inline-block;
                margin-bottom: 20px;
                background: #A53860;
                color: #fff;
                padding: 10px 16px;
                border-radius: 8px;
                text-decoration: none;
                font-weight: bold;
                transition: background 0.3s;
            }

            .add-btn:hover {
                background: #EF88AD;
            }

            .property-card {
                border: 1px solid #f0d9e8;
                padding: 20px;
                border-radius: 12px;
                margin-bottom: 16px;
                background: #ffffff;
                box-shadow: 0 4px 12px rgba(58, 5, 105, 0.05);
                transition: transform 0.2s;
            }

            .property-card:hover {
                transform: translateY(-3px);
            }

            .property-card h3 {
                margin-top: 0;
                color: #670D2F;
            }

            .property-card p {
                margin: 6px 0;
            }

            .action-links a {
                margin-right: 8px;
                text-decoration: none;
                color: #670D2F;
                font-weight: bold;
                transition: color 0.3s;
            }

            .action-links a:hover {
                color: #A53860;
            }

            .back-link {
                display: inline-block;
                margin-top: 30px;
                text-decoration: none;
                color: #670D2F;
                font-weight: bold;
                transition: color 0.3s;
            }

            .back-link:hover {
                color: #A53860;
            }

            .no-property {
                text-align: center;
                color: #999;
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
            <h2>Property List</h2>

            <!-- ✅ Only admin & host can add property -->
            <c:if test="${sessionScope.user != null && (sessionScope.user.role == 'admin' || sessionScope.user.role == 'host')}">
                <a href="property-add.jsp" class="add-btn">Add New Property</a>
            </c:if>

            <c:if test="${empty properties}">
                <p class="no-property">No properties found.</p>
            </c:if>

            <c:forEach var="p" items="${properties}">
                <div class="property-card">
                    <h3>${p.name}</h3>
                    <p>${p.description}</p>
                    <p>Price per night: ${p.pricePerNight}</p>

                    <div class="action-links">
                        <!-- 🟢 Anyone can view details -->
                        <a href="PropertyController?action=propertyDetail&id=${p.propertyId}">View Details</a>

                        <!-- ✅ Admin can edit/delete all -->
                        <c:if test="${sessionScope.user != null && sessionScope.user.role == 'admin'}">
                            | <a href="PropertyController?action=propertyEdit&id=${p.propertyId}">Edit</a>
                            | <a href="PropertyController?action=propertyDelete&id=${p.propertyId}" onclick="return confirm('Are you sure you want to delete this property?');">Delete</a>
                        </c:if>

                        <!-- ✅ Host can edit/delete own properties -->
                        <c:if test="${sessionScope.user != null && sessionScope.user.role == 'host' && sessionScope.user.userID == p.hostId}">
                            | <a href="PropertyController?action=propertyEdit&id=${p.propertyId}">Edit</a>
                            | <a href="PropertyController?action=propertyDelete&id=${p.propertyId}" onclick="return confirm('Are you sure you want to delete this property?');">Delete</a>
                        </c:if>
                    </div>
                </div>
            </c:forEach>

            <div style="text-align: center;">
                <a href="welcome.jsp" class="back-link">Back to Home</a>
            </div>
        </div>

        <footer>
            © 2025 Booking System | Designed by Nhi & Thuong ✨
        </footer>

    </body>
</html>
