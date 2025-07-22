<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.PolicyDTO" %>
<%
    List<PolicyDTO> policies = (List<PolicyDTO>) request.getAttribute("policies");
    String message = (String) request.getAttribute("message");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Policy List</title>
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
            align-items: flex-start;
            padding: 40px 20px;
        }

        .policy-container {
            background: #ffffff;
            padding: 40px;
            border-radius: 12px;
            box-shadow: 0 8px 24px rgba(58, 5, 105, 0.08);
            width: 100%;
            max-width: 600px;
            border: 1px solid #f0d9e8;
            transition: transform 0.3s ease;
        }

        .policy-container:hover {
            transform: translateY(-4px);
        }

        h2 {
            text-align: center;
            color: #A53860;
            margin-bottom: 20px;
        }

        ul {
            list-style-type: none;
            padding: 0;
        }

        li {
            background: #fdfbff;
            margin-bottom: 10px;
            padding: 12px 16px;
            border: 1px solid #ccc;
            border-radius: 8px;
            color: #3A0CA3;
        }

        .message {
            margin-bottom: 16px;
            padding: 10px;
            background-color: #fde4ec;
            border-left: 4px solid #EF88AD;
            color: #A53860;
            border-radius: 6px;
            font-size: 13px;
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
    <div class="policy-container">
        <h2>Policy List</h2>
        <% if (message != null) { %>
            <div class="message"><%= message %></div>
        <% } %>
        <ul>
            <% if (policies != null && !policies.isEmpty()) {
                   for (PolicyDTO p : policies) { %>
                <li><b><%= p.getTitle() %></b>: <%= p.getDescription() %></li>
            <%     }
               } else { %>
               <li>No data available!</li>
            <% } %>
        </ul>
    </div>
</div>

<footer>
    © 2025 Booking System | Designed by Nhi&Thuong ✨
</footer>

</body>
</html>
