<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.HouseRuleDTO" %>
<%
    List<HouseRuleDTO> rules = (List<HouseRuleDTO>) request.getAttribute("rules");
    String message = (String) request.getAttribute("message");
    int propertyId = (Integer) request.getAttribute("propertyId");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>House Rules</title>
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
            text-align: center;
            color: #A53860;
            margin-bottom: 20px;
        }

        .message {
            background: #fde4ec;
            color: #A53860;
            padding: 10px;
            border-left: 4px solid #EF88AD;
            border-radius: 8px;
            margin-bottom: 20px;
        }

        ul {
            list-style-type: none;
            padding: 0;
        }

        li {
            padding: 10px;
            border: 1px solid #f0d9e8;
            margin-bottom: 8px;
            border-radius: 8px;
            background: #fdfbff;
        }

        form {
            margin-top: 20px;
            display: flex;
            flex-direction: column;
            gap: 10px;
        }

        input[type="text"] {
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 8px;
            background-color: #fdfbff;
        }

        button {
            background-color: #A53860;
            color: #fff;
            padding: 10px;
            border: none;
            border-radius: 8px;
            font-weight: bold;
            cursor: pointer;
            transition: background 0.3s;
        }

        button:hover {
            background-color: #EF88AD;
        }

        .back-link {
            display: block;
            text-align: center;
            margin-top: 30px;
            color: #670D2F;
            text-decoration: none;
            font-weight: bold;
        }

        .back-link:hover {
            color: #A53860;
        }
    </style>
</head>
<body>

<header>
    🏡 Haus Thurni – House Rules
</header>

<div class="main-content">
    <h2>Rules for Property ID: <%= propertyId %></h2>

    <% if (message != null) { %>
        <div class="message"><%= message %></div>
    <% } %>

    <ul>
        <% for (HouseRuleDTO r : rules) { %>
            <li><%= r.getRuleText() %></li>
        <% } %>
        <% if (rules == null || rules.isEmpty()) { %>
            <li>No rules defined yet.</li>
        <% } %>
    </ul>

    <form action="PolicyRuleController" method="post">
        <input type="hidden" name="action" value="addRule" />
        <input type="hidden" name="propertyId" value="<%= propertyId %>" />
        <input type="text" name="ruleText" placeholder="Enter new rule..." required />
        <button type="submit">➕ Add Rule</button>
    </form>

    <a class="back-link" href="welcome.jsp">← Back to Home</a>
</div>

<footer>
    © 2025 Booking System | Designed by Nhi & Thuong ✨
</footer>

</body>
</html>
