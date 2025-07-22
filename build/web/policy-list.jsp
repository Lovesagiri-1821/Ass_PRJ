<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Policy Management (Admin)</title>
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
                padding: 40px 60px;
                border-radius: 0;
                box-shadow: none;
                width: 100%;
                border-top: 1px solid #f0d9e8;
                border-bottom: 1px solid #f0d9e8;
                box-sizing: border-box;
            }



            .policy-container:hover {
                transform: translateY(-4px);
            }

            h2 {
                text-align: center;
                color: #A53860;
                margin-bottom: 20px;
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

            a {
                color: #670D2F;
                text-decoration: none;
                font-weight: bold;
                margin-right: 8px;
                transition: color 0.3s;
            }

            a:hover {
                color: #A53860;
            }

            button {
                background: none;
                border: none;
                color: #A53860;
                cursor: pointer;
                font-weight: bold;
                margin-left: 5px;
                transition: color 0.3s;
            }

            button:hover {
                color: #EF88AD;
            }

            .actions {
                margin-top: 20px;
                text-align: center;
            }

            .actions a {
                display: inline-block;
                margin: 0 10px;
                background: #A53860;
                color: #fff;
                padding: 10px 16px;
                border-radius: 8px;
                text-decoration: none;
                transition: background 0.3s, transform 0.2s;
            }

            .actions a:hover {
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
            <div class="policy-container">
                <h2>Policy Management (Admin)</h2>

                <c:if test="${not empty message}">
                    <div class="message">${message}</div>
                </c:if>

                <ul class="policy-list">
                    <c:forEach var="p" items="${policies}">
                        <li>
                            <b>${p.title}</b>: ${p.description}
                            | <a href="policy-edit.jsp?id=${p.policyId}&title=${p.title}&description=${p.description}">Edit</a>
                            | 
                            <form action="PolicyController" method="post" style="display:inline;" onsubmit="return confirm('Are you sure you want to delete this?');">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="policyId" value="${p.policyId}">
                                <button type="submit">Delete</button>
                            </form>
                        </li>
                    </c:forEach>
                    <c:if test="${empty policies}">
                        <li>No data available!</li>
                        </c:if>
                </ul>

                <div class="actions">
                    <a href="policy-add.jsp">Add New Policy</a>
                    <a href="welcome.jsp">Back to Home</a>
                </div>
            </div>
        </div>

        <footer>
            © 2025 Booking System | Designed by Nhi&Thuong ✨
        </footer>

    </body>
</html>
