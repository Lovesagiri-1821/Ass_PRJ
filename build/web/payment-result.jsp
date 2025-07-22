<%-- 
    Document   : payment-result
    Created on : Jul 21, 2025, 4:45:00 PM
    Author     : Admin
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Payment Result</title>
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
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 40px 20px;
        }

        .result-container {
            background: #ffffff;
            padding: 40px;
            border-radius: 12px;
            box-shadow: 0 8px 24px rgba(58, 5, 105, 0.08);
            width: 100%;
            max-width: 500px;
            border: 1px solid #f0d9e8;
            text-align: center;
            transition: transform 0.3s ease;
        }

        .result-container:hover {
            transform: translateY(-4px);
        }

        h2 {
            color: #A53860;
            margin-bottom: 20px;
        }

        .message-success {
            color: green;
            font-weight: bold;
            margin-bottom: 16px;
        }

        .message-error {
            color: red;
            font-weight: bold;
            margin-bottom: 16px;
        }

        .message-wait {
            color: gray;
            font-weight: bold;
            margin-bottom: 16px;
        }

        a {
            display: inline-block;
            margin-top: 12px;
            background: #A53860;
            color: #fff;
            padding: 10px 16px;
            border-radius: 8px;
            text-decoration: none;
            font-weight: bold;
            transition: background 0.3s, transform 0.2s;
        }

        a:hover {
            background: #EF88AD;
            transform: scale(1.03);
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
    <div class="result-container">
        <h2>Payment Result</h2>

        <c:choose>
            <c:when test="${paymentSuccess eq true}">
                <p class="message-success">🎉 Payment successful! Thank you for your booking.</p>
                <a href="MainController?action=propertyList">⬅ Back to Home</a>
            </c:when>

            <c:when test="${paymentSuccess eq false}">
                <p class="message-error">❌ Failed to update payment. Please contact support.</p>
                <a href="payment.jsp">🔁 Try Again</a>
            </c:when>

            <c:otherwise>
                <p class="message-wait">⏳ Processing payment...</p>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<footer>
    © 2025 Booking System | Designed by Nhi&Thuong ✨
</footer>

</body>
</html>
