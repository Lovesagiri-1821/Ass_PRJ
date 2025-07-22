<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.UserDTO" %>
<%
    UserDTO user = (UserDTO) request.getAttribute("user");
    UserDTO currentUser = (UserDTO) session.getAttribute("user");
    String message = (String) request.getAttribute("message");
    boolean isAdmin = currentUser != null && "admin".equalsIgnoreCase(currentUser.getRole());
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Update User</title>
    <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@300;500;700&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Outfit', sans-serif;
            background-color: #fff;
            margin: 0;
            padding: 0;
            color: #333;
        }

        header, footer {
            background-color: #f8f0fa;
            text-align: center;
            padding: 15px 30px;
            font-weight: bold;
            color: #670D2F;
        }

        .form-container {
            max-width: 500px;
            margin: 50px auto;
            padding: 30px;
            border-radius: 12px;
            background-color: #ffffff;
            border: 1px solid #f0d9e8;
            box-shadow: 0 8px 24px rgba(58, 5, 105, 0.08);
        }

        h2 {
            text-align: center;
            color: #A53860;
        }

        label {
            display: block;
            margin-top: 14px;
            font-weight: bold;
            color: #3A0CA3;
        }

        input[type="text"], input[type="email"], input[type="password"], select {
            width: 100%;
            padding: 10px;
            margin-top: 4px;
            border-radius: 8px;
            border: 1px solid #ccc;
            background-color: #fdfbff;
            transition: border-color 0.3s, box-shadow 0.3s;
        }

        input:focus, select:focus {
            border-color: #EF88AD;
            box-shadow: 0 0 4px #EF88AD55;
            outline: none;
        }

        input[type="submit"] {
            margin-top: 20px;
            padding: 12px;
            width: 100%;
            border: none;
            border-radius: 8px;
            background: #A53860;
            color: #fff;
            font-weight: bold;
            cursor: pointer;
            transition: background 0.3s;
        }

        input[type="submit"]:hover {
            background: #EF88AD;
        }

        .message {
            color: green;
            text-align: center;
            margin-bottom: 10px;
        }

        .back-link {
            display: block;
            text-align: center;
            margin-top: 20px;
            text-decoration: none;
            font-weight: bold;
            color: #A53860;
        }

        .back-link:hover {
            color: #EF88AD;
        }
    </style>
</head>
<body>

<header>
    🌸 Update User Profile 🌸
</header>

<div class="form-container">
    <h2>Update User</h2>

    <% if (message != null) { %>
        <div class="message"><%= message %></div>
    <% } %>

    <% if (user != null) { %>
        <form action="UserController" method="post">
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="userId" value="<%= user.getUserID() %>">

            <label>Name:</label>
            <input type="text" name="name" value="<%= user.getName() %>" required>

            <label>Email:</label>
            <input type="email" name="email" value="<%= user.getEmail() %>" required>

            <label>New Password (leave blank to keep current):</label>
            <input type="password" name="password" placeholder="New password">

            <% if (isAdmin) { %>
                <label>Role:</label>
                <input type="text" name="role" value="<%= user.getRole() %>" required>
            <% } else { %>
                <input type="hidden" name="role" value="<%= user.getRole() %>">
            <% } %>

            <label>Status:</label>
            <select name="isDeleted">
                <option value="0" <%= !user.isDeleted() ? "selected" : "" %>>Active</option>
                <option value="1" <%= user.isDeleted() ? "selected" : "" %>>Deleted</option>
            </select>

            <input type="submit" value="Update">
        </form>
    <% } else { %>
        <p style="color: red; text-align: center;">User information not found.</p>
    <% } %>

    <a href="UserController?action=list" class="back-link">← Back to user list</a>
</div>

<footer>
    © 2025 Booking System | Designed by Nhi & Thuong ✨
</footer>

</body>
</html>
