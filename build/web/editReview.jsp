<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>✏️ Edit Review</title>
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
            max-width: 600px;
            margin: auto;
        }

        h2 {
            color: #A53860;
            text-align: center;
            margin-bottom: 20px;
        }

        label {
            font-weight: bold;
            color: #3A0CA3;
        }

        input[type="number"], textarea {
            width: 100%;
            padding: 12px;
            margin: 8px 0 16px 0;
            border: 1px solid #ccc;
            border-radius: 8px;
            background-color: #fdfbff;
            transition: border-color 0.3s, box-shadow 0.3s;
        }

        input[type="number"]:focus, textarea:focus {
            border-color: #EF88AD;
            box-shadow: 0 0 4px #EF88AD55;
            outline: none;
        }

        button {
            background-color: #A53860;
            color: white;
            padding: 10px 16px;
            border: none;
            border-radius: 8px;
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
            margin-top: 16px;
            color: #670D2F;
            text-decoration: none;
            font-weight: bold;
            transition: color 0.3s;
        }

        .back-link:hover {
            color: #A53860;
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
    <h2>✏️ Edit Review</h2>

    <form action="${pageContext.request.contextPath}/ReviewController" method="post">
        <input type="hidden" name="action" value="updateReview"/>
        <input type="hidden" name="reviewId" value="${review.reviewId}"/>
        <input type="hidden" name="propertyId" value="${review.propertyId}"/>

        <label>⭐ Rating:</label>
        <input type="number" name="rating" value="${review.rating}" min="1" max="5" required/>

        <label>💬 Comment:</label>
        <textarea name="comment" rows="3" required>${review.comment}</textarea>

        <button type="submit">💾 Save Changes</button>
    </form>

    <a class="back-link" href="javascript:history.back()">⬅️ Go Back</a>
</div>

<footer>
    © 2025 Booking System | Designed by Nhi&Thuong ✨
</footer>

</body>
</html>
