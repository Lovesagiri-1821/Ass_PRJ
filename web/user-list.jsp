<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.UserDTO" %>
<%
    List<UserDTO> userList = (List<UserDTO>) request.getAttribute("userList");
    String message = (String) request.getAttribute("message");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>User Management</title>
        <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@300;500;700&display=swap" rel="stylesheet">
        <style>
            body {
                font-family: 'Outfit', sans-serif;
                margin: 0;
                padding: 0;
                background-color: #fff;
                color: #333;
            }

            header, footer {
                background-color: #f8f0fa;
                text-align: center;
                padding: 15px 30px;
                font-weight: bold;
                color: #670D2F;
            }

            .main-content {
                max-width: 900px;
                margin: 40px auto;
                padding: 0 30px;
            }

            h2 {
                text-align: center;
                color: #A53860;
            }

            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
                background-color: #fdfbff;
                border: 1px solid #f0d9e8;
                border-radius: 12px;
                overflow: hidden;
            }

            th, td {
                border-bottom: 1px solid #f0d9e8;
                padding: 12px;
                text-align: left;
            }

            th {
                background-color: #f8f0fa;
                color: #670D2F;
            }

            tr:hover {
                background-color: #fff3f7;
            }

            .inline {
                display: inline;
            }

            input[type="submit"] {
                padding: 8px 12px;
                border-radius: 6px;
                border: 1px solid #ccc;
                background-color: #A53860;
                color: #fff;
                font-weight: bold;
                cursor: pointer;
                margin: 4px 2px;
                transition: background 0.3s;
            }

            input[type="submit"]:hover {
                background-color: #EF88AD;
            }

            .error {
                color: red;
                text-align: center;
            }

            a {
                text-decoration: none;
                color: #A53860;
                font-weight: bold;
            }

            a:hover {
                color: #EF88AD;
            }

            hr {
                margin: 40px 0;
                border: none;
                height: 1px;
                background: #eee;
            }
        </style>
        <script>
            function confirmDelete(form) {
                if (confirm("Are you sure you want to soft delete this user?")) {
                    form.submit();
                }
            }
        </script>
    </head>
    <body>

        <header>
            🌸 User Management Dashboard 🌸
        </header>

        <div class="main-content">
            <h2>User List</h2>

            <% if (message != null) { %>
            <p class="error"><%= message %></p>
            <% } %>

            <table>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Status</th>
                    <th>Created At</th>
                    <th>Actions</th>
                </tr>
                <% if (userList != null) {
            for (UserDTO user : userList) { %>
                <tr>
                    <td><%= user.getUserID() %></td>
                    <td><%= user.getName() %></td>
                    <td><%= user.getEmail() %></td>
                    <td><%= user.isDeleted() ? "Deleted" : "Active" %></td>
                    <td><%= user.getCreatedAt() != null ? user.getCreatedAt() : "" %></td>
                    <td>
                        <form class="inline" action="UserController" method="get">
                            <input type="hidden" name="action" value="showUpdatePage">
                            <input type="hidden" name="userId" value="<%= user.getUserID() %>">
                            <input type="submit" value="Update">
                        </form>
                        <form class="inline" action="UserController" method="post"
                              onsubmit="event.preventDefault(); confirmDelete(this);">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="userId" value="<%= user.getUserID() %>">
                            <input type="submit" value="Soft Delete">
                        </form>
                    </td>
                </tr>
                <%  }
        } %>
            </table>

            <hr>
            <p style="text-align: center;"><a href="welcome.jsp">Back to Home</a></p>
        </div>

        <footer>
            © 2025 Booking System | Designed by Nhi & Thuong ✨
        </footer>

    </body>
</html>
