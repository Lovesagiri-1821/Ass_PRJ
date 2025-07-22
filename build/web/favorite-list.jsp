<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Favorite List</title>
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

        .message {
            color: green;
            text-align: center;
            margin-bottom: 16px;
            font-weight: bold;
        }

        .favorite-item {
            border: 1px solid #f0d9e8;
            background: #fdfbff;
            padding: 20px;
            border-radius: 12px;
            margin-bottom: 16px;
            box-shadow: 0 4px 12px rgba(58, 5, 105, 0.08);
            transition: transform 0.3s ease;
        }

        .favorite-item:hover {
            transform: translateY(-4px);
        }

        h3 {
            color: #670D2F;
            margin-bottom: 8px;
        }

        p {
            margin: 6px 0;
        }

        button {
            margin-top: 10px;
            padding: 10px 16px;
            border: none;
            border-radius: 8px;
            background: #A53860;
            color: #fff;
            font-weight: bold;
            cursor: pointer;
            transition: background 0.3s, transform 0.2s;
        }

        button:hover {
            background: #EF88AD;
            transform: scale(1.03);
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

        .no-favorite {
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
    <h2>💖 Favorite Properties List</h2>

    <c:if test="${not empty message}">
        <div class="message">${message}</div>
    </c:if>

    <c:if test="${empty favoriteList}">
        <p class="no-favorite">You don't have any properties in your favorites list.</p>
    </c:if>

    <c:forEach var="property" items="${favoriteList}">
        <div class="favorite-item">
            <h3>${property.name}</h3>
            <p><strong>Description:</strong> ${property.shortDescription}</p>
            <p><strong>Price per night:</strong> ${property.pricePerNight} VND</p>
            <p><strong>Status:</strong> ${property.status}</p>
            <form action="FavoriteController" method="post">
                <input type="hidden" name="action" value="removeFavorite" />
                <input type="hidden" name="propertyId" value="${property.propertyId}" />
                <button type="submit">❌ Remove from favorites</button>
            </form>
        </div>
    </c:forEach>

    <a class="back-link" href="MainController">⬅️ Back to main page</a>
</div>

<footer>
    © 2025 Booking System | Designed by Nhi&Thuong ✨
</footer>

</body>
</html>
