<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Login</title>
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

            .login-container {
                background: #ffffff;
                padding: 40px;
                border-radius: 12px;
                box-shadow: 0 8px 24px rgba(58, 5, 105, 0.08);
                width: 100%;
                max-width: 360px;
                border: 1px solid #f0d9e8;
                transition: transform 0.3s ease;
            }

            .login-container:hover {
                transform: translateY(-4px);
            }

            h2 {
                margin-bottom: 20px;
                text-align: center;
                color: #A53860;
            }

            label {
                display: block;
                margin-top: 15px;
                font-size: 14px;
                color: #3A0CA3;
            }

            input[type="text"],
            input[type="password"] {
                width: 100%;
                padding: 12px 14px;
                margin-top: 6px;
                border: 1px solid #ccc;
                border-radius: 8px;
                background-color: #fdfbff;
                color: #333;
                transition: border-color 0.3s, box-shadow 0.3s;
            }

            input[type="text"]:focus,
            input[type="password"]:focus {
                border-color: #EF88AD;
                box-shadow: 0 0 4px #EF88AD55;
                outline: none;
            }

            input[type="submit"] {
                width: 100%;
                margin-top: 24px;
                padding: 12px;
                border: none;
                border-radius: 8px;
                background: #A53860;
                color: #fff;
                font-weight: bold;
                cursor: pointer;
                transition: background 0.3s, transform 0.2s;
            }

            input[type="submit"]:hover {
                background: #EF88AD;
                transform: scale(1.03);
            }

            p {
                margin-top: 16px;
                text-align: center;
                font-size: 14px;
            }

            a {
                color: #670D2F;
                text-decoration: none;
                font-weight: bold;
                transition: color 0.3s;
            }

            a:hover {
                color: #A53860;
            }

            .error-message {
                margin-top: 12px;
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
                <!-- Cột trái: ảnh -->
                <div style="flex: 1; display: flex; justify-content: flex-start;">
                    <img src="images/header.jpg" alt="Logo" style="width: 80px; height: 80px; border-radius: 50%; padding: 10px ;margin-left: 30px;">
                </div>

                <!-- Cột giữa: chữ trung tâm -->
                <div style="flex: 2; text-align: center;">
                    <span style="font-weight: bold; color: #670D2F; font-size: 18px;">
                        🌸 Welcome to Haus Thurni 🌸
                    </span>
                </div>

                <!-- Cột phải: để trống (để chữ ở giữa thực sự) -->
                <div style="flex: 1;"></div>
            </div>
        </header>



        <div class="main-content">
            <c:choose>
                <c:when test="${not empty sessionScope.user}">
                    <c:redirect url="welcome.jsp"/>
                </c:when>
                <c:otherwise>
                    <div class="login-container">
                        <h2>Login</h2>
                        <form action="MainController" method="post">
                            <input type="hidden" name="action" value="login"/>

                            <label for="email">Email:</label>
                            <input type="text" id="email" name="strEmail" placeholder="Enter your email" required/>

                            <label for="password">Password:</label>
                            <input type="password" id="password" name="strPassword" placeholder="Enter your password" required/>

                            <input type="submit" value="Login"/>
                        </form>

                        <p>Don't have an account? <a href="register.jsp">Register now</a></p>

                        <c:if test="${not empty requestScope.message}">
                            <div class="error-message">${requestScope.message}</div>
                        </c:if>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

        <footer>
            © 2025 Booking System | Designed by Nhi&Thuong ✨
        </footer>

    </body>
</html>
