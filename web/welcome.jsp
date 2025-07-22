<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Welcome</title>

        <!-- Google font -->
        <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@300;500;700&display=swap" rel="stylesheet">
        <!-- Font‑Awesome 6 (icons) -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

        <style>
            body {
                margin: 0;
                font-family: 'Outfit', sans-serif;
                background: #fff;
                color: #333;
            }
            header, footer {
                background: #f8f0fa;
                padding: 16px 30px;
                color: #670D2F;
                font-weight: bold;
                text-align: center;
            }
            header .bar {
                display: flex;
                align-items: center;
                justify-content: space-between;
            }
            header .bar .logo {
                flex: 1;
                display: flex;
            }
            header .bar .logo img {
                width: 60px;
                height: 60px;
                border-radius: 50%;
            }
            header .bar .title {
                flex: 2;
                text-align: center;
                font-size: 18px;
            }
            header .account-menu {
                flex: 1;
                text-align: right;
                position: relative;
            }
            .account-btn {
                background-color: #3A0CA3;
                color: white;
                padding: 10px 14px;
                font-size: 14px;
                border: none;
                border-radius: 6px;
                cursor: pointer;
            }
            .account-menu:hover .dropdown {
                display: block;
            }
            .dropdown {
                display: none;
                position: absolute;
                right: 0;
                background-color: #fff;
                min-width: 200px;
                box-shadow: 0px 8px 16px rgba(0,0,0,0.1);
                border-radius: 10px;
                overflow: hidden;
                z-index: 999;
            }
            .dropdown a, .dropdown form {
                padding: 10px 16px;
                text-decoration: none;
                display: block;
                color: #333;
                font-size: 14px;
                font-weight: normal;
            }
            .dropdown a:hover, .dropdown form:hover {
                background-color: #f8f0fa;
            }
            .logout-btn {
                width: 100%;
                border: none;
                background: none;
                color: #A53860;
                text-align: left;
                padding: 10px 16px;
                cursor: pointer;
                font-size: 14px;
                font-weight: normal;
            }
            .logout-btn:hover {
                background-color: #f8f0fa;
            }

            h2 {
                text-align: center;
                color: #A53860;
                margin: 30px 0;
            }
            a {
                color: #A53860;
                text-decoration: none;
            }
            a:hover {
                text-decoration: underline;
            }
            .center {
                text-align: center;
            }
            .btn {
                padding: 8px 16px;
                border: none;
                border-radius: 6px;
                cursor: pointer;
                font-size: 14px;
                color: #fff;
                background: #A53860;
                transition: background .25s;
            }
            .btn:hover {
                background: #EF88AD;
            }
            .property-card .btn {
                background: #3A0CA3;
            }
            .property-card .btn:hover {
                background: #670D2F;
            }

            .search-bar {
                text-align: center;
                margin: 30px 0 10px;
            }
            .search-bar input[type="text"],
            .search-bar select {
                padding: 8px;
                border: 1px solid #ccc;
                border-radius: 6px;
                margin-right: 8px;
                background: #fdfbff;
            }

            .advanced-filters {
                display: flex;
                justify-content: center;
                margin: 20px 0;
            }

            .advanced-filters details {
                width: fit-content;
            }

            .advanced-filters summary {
                font-size: 15px;
                padding: 8px 16px;
                background: #f8f0fa;
                border-radius: 6px;
                cursor: pointer;
                user-select: none;
                text-align: center;
                border: 1px solid #ddd;
            }
            .advanced-filters details[open] summary {
                background: #e9dff1;
            }

            .advanced-filters .box {
                margin-top: 10px;
                padding: 10px;
                border: 1px solid #ddd;
                border-radius: 6px;
                background: #fdfbff;
                display: flex;
                flex-wrap: wrap;
                gap: 12px;
            }

            .advanced-filters .box label {
                font-size: 14px;
                white-space: nowrap;
            }

            .property-card {
                border: 1px solid #eee;
                padding: 16px;
                margin: 12px;
                border-radius: 10px;
                width: 300px;
                display: inline-block;
                vertical-align: top;
                background: #fefefe;
                box-shadow: 0 2px 8px rgba(0,0,0,0.04);
            }

            .property-card h3 {
                margin-top: 0;
                color: #3A0CA3;
            }

            .policy-card {
                border: 1px solid #ddd;
                padding: 14px;
                margin: 12px;
                border-radius: 8px;
                background: #fafafa;
            }

            .policy-card h4 {
                margin: 0;
                color: #670D2F;
            }

            footer {
                margin-top: 40px;
                font-size: 14px;
            }
        </style>
    </head>
    <body>

        <header>
            <div class="bar">
                <div class="logo">
                    <img src="images/header.jpg" alt="Logo">
                </div>
                <div class="title">🌸 Welcome to Haus Thurni 🌸</div>
                <div class="account-menu">
                    <button class="account-btn">
                        ${sessionScope.user.name}
                        <i class="fa-solid fa-caret-down"></i>
                    </button>
                    <div class="dropdown">
                        <a href="UserController?action=list">Account Information</a>
                        <form action="UserController" method="post">
                            <input type="hidden" name="action" value="logout">
                            <button type="submit" class="logout-btn"> Logout</button>
                        </form>
                    </div>
                </div>
            </div>
        </header>

        <c:choose>
            <c:when test="${not empty sessionScope.user}">
                <h2>Welcome, ${sessionScope.user.name}!</h2>

                <!-- Search bar -->
                <form action="MainController" method="get" class="search-bar">
                    <input type="hidden" name="action" value="propertyFilter">
                    <input type="text" name="keyword" placeholder="Where are you going?">
                    <select name="guests">
                        <option value="">Guests</option>
                        <option value="1">1 guest</option>
                        <option value="2">2 guests</option>
                        <option value="3">3 guests</option>
                        <option value="4">4 guests</option>
                        <option value="5">5+ guests</option>
                    </select>

                    <br><br>
                    <button type="submit" class="btn"><i class="fa-solid fa-magnifying-glass"></i> Search</button>
                </form>

                <!-- Advanced Filters -->
                <div class="advanced-filters">
                    <details>
                        <summary><i class="fa-solid fa-filter"></i> Advanced Filters</summary>
                        <div class="box">
                            <label><input type="checkbox" name="hasWifi"> <i class="fa-solid fa-wifi"></i> Wi‑Fi</label>
                            <label><input type="checkbox" name="hasPrivatePool"> <i class="fa-solid fa-water-ladder"></i> Private Pool</label>
                            <label><input type="checkbox" name="hasBalcony"> <i class="fa-solid fa-person-shelter"></i> Balcony</label>
                            <label><input type="checkbox" name="hasParking"> <i class="fa-solid fa-square-parking"></i> Parking</label>
                            <label><input type="checkbox" name="allowsPets"> <i class="fa-solid fa-dog"></i> Pet Friendly</label>
                            <label><input type="checkbox" name="allowsSmoking"> <i class="fa-solid fa-smoking"></i> Smoking</label>
                            <label><input type="checkbox" name="nearLake"> <i class="fa-solid fa-water"></i> Lake View</label>
                            <label><input type="checkbox" name="nearBeach"> <i class="fa-solid fa-umbrella-beach"></i> Sea View</label>
                            <label><input type="checkbox" name="nearCityCenter"> <i class="fa-solid fa-city"></i> City Center</label>
                        </div>
                    </details>
                </div>

                <!-- Property list -->
                <c:forEach var="p" items="${properties}">
                    <div class="property-card">
                        <h3>${p.name}</h3>
                        <p>${p.description}</p>
                        <p><strong>Price:</strong> $${p.pricePerNight} / night</p>
                        <p><strong>Guests:</strong> ${p.numGuests}</p>
                        <c:if test="${sessionScope.user.role eq 'admin' or sessionScope.user.role eq 'host'}">
                            <form action="PropertyController" method="get">
                                <input type="hidden" name="action" value="propertyEdit">
                                <input type="hidden" name="id" value="${p.propertyId}">
                                <button type="submit" class="btn">Edit</button>
                            </form>
                        </c:if>
                    </div>
                </c:forEach>

                <c:if test="${sessionScope.user.role eq 'host'}">
                    <div class="center" style="margin:40px 0;">
                        <a href="PropertyController?action=propertyHostList"><button class="btn">🏠 View My Properties</button></a>
                    </div>
                </c:if>

                <p class="center" style="margin-top:30px;">
                    <a href="MainController?action=propertyList">View All Properties</a>
                </p>

                <c:forEach var="p" items="${requestScope.policies}">
                    <div class="policy-card">
                        <h4>${p.title}</h4>
                        <p>${p.description}</p>
                        <c:if test="${sessionScope.user.role eq 'admin'}">
                            <p>
                                <a href="policy-edit.jsp?id=${p.policyId}&title=${p.title}&description=${p.description}">✏️ Edit</a> |
                            <form action="PolicyController" method="post" style="display:inline;" onsubmit="return confirm('Delete this policy?');">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="policyId" value="${p.policyId}">
                                <button type="submit" class="btn" style="margin-top:6px;">🗑 Delete</button>
                            </form>
                        </p>
                    </c:if>
                </div>
            </c:forEach>

            <c:if test="${sessionScope.user.role eq 'admin'}">
                <div class="center" style="margin-top:20px;">
                    <p class="center" style="margin-top:10px;">
                        <a href="PolicyController?action=list">Manage All Policies</a>
                    </p>

                </div>
            </c:if>
        </c:when>

        <c:otherwise>
            <c:redirect url="login.jsp"/>
        </c:otherwise>
    </c:choose>

    <footer>
        © 2025 Booking System | Designed by Nhi & Thuong ✨
    </footer>

</body>
</html> 