<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Policy</title>
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

        .form-container {
            background: #ffffff;
            padding: 40px;
            border-radius: 12px;
            box-shadow: 0 8px 24px rgba(58, 5, 105, 0.08);
            width: 100%;
            max-width: 500px;
            border: 1px solid #f0d9e8;
            transition: transform 0.3s ease;
        }

        .form-container:hover {
            transform: translateY(-4px);
        }

        h2 {
            text-align: center;
            color: #A53860;
            margin-bottom: 20px;
        }

        label {
            display: block;
            margin-top: 12px;
            font-size: 14px;
            color: #3A0CA3;
        }

        input[type="text"] {
            width: 100%;
            padding: 12px 14px;
            margin-top: 6px;
            border: 1px solid #ccc;
            border-radius: 8px;
            background-color: #fdfbff;
            color: #333;
            transition: border-color 0.3s, box-shadow 0.3s;
        }

        input[type="text"]:focus {
            border-color: #EF88AD;
            box-shadow: 0 0 4px #EF88AD55;
            outline: none;
        }

        button[type="submit"] {
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

        button[type="submit"]:hover {
            background: #EF88AD;
            transform: scale(1.03);
        }

        .back-link {
            display: block;
            text-align: center;
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
    <div class="form-container">
        <h2>Edit Policy</h2>
        <form action="PolicyController" method="post">
            <input type="hidden" name="action" value="update" />
            <input type="hidden" name="policyId" value="${param.id}" />

            <label for="title">Title:</label>
            <input type="text" id="title" name="title" value="${param.title}" required>

            <label for="description">Description:</label>
            <input type="text" id="description" name="description" value="${param.description}" required>

            <button type="submit">💾 Save Changes</button>
        </form>
        <a class="back-link" href="PolicyController?action=list">⬅ Back to list</a>
    </div>
</div>

<footer>
    © 2025 Booking System | Designed by Nhi&Thuong ✨
</footer>

</body>
</html>
