<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Property</title>
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
            max-width: 600px;
            border: 1px solid #f0d9e8;
            transition: transform 0.3s ease;
        }

        .form-container:hover {
            transform: translateY(-4px);
        }

        h2, h4 {
            margin-bottom: 15px;
            color: #A53860;
        }

        label {
            display: block;
            margin-top: 12px;
            font-size: 14px;
            color: #3A0CA3;
        }

        input[type="text"],
        input[type="number"],
        textarea {
            width: 100%;
            padding: 12px 14px;
            margin-top: 6px;
            border: 1px solid #ccc;
            border-radius: 8px;
            background-color: #fdfbff;
            color: #333;
            transition: border-color 0.3s, box-shadow 0.3s;
            resize: vertical;
        }

        input[type="text"]:focus,
        input[type="number"]:focus,
        textarea:focus {
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

        .checkbox-group {
            margin-top: 10px;
        }

        .checkbox-group label {
            display: inline-block;
            margin-right: 10px;
            margin-top: 8px;
            font-size: 13px;
            color: #670D2F;
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
        <h2>Add New Property</h2>
        <form action="PropertyController" method="post">
            <input type="hidden" name="action" value="propertyAdd" />

            <label for="name">Name:</label>
            <input type="text" id="name" name="name" required>

            <label for="price">Price per Night:</label>
            <input type="text" id="price" name="price_per_night" required>

            <label for="location_id">Location ID:</label>
            <input type="number" id="location_id" name="location_id" required>

            <label for="num_bedrooms">Bedrooms:</label>
            <input type="number" id="num_bedrooms" name="num_bedrooms" required>

            <label for="num_bathrooms">Bathrooms:</label>
            <input type="number" id="num_bathrooms" name="num_bathrooms" required>

            <label for="num_guests">Number of Guests:</label>
            <input type="number" id="num_guests" name="num_guests" required>

            <label for="num_rooms">Number of Rooms:</label>
            <input type="number" id="num_rooms" name="num_rooms" required>

            <label for="short_description">Short Description:</label>
            <input type="text" id="short_description" name="short_description">

            <label for="description">Detailed Description:</label>
            <textarea id="description" name="description"></textarea>

            <label for="thumbnail_url">Thumbnail URL:</label>
            <input type="text" id="thumbnail_url" name="thumbnail_url">

            <h4>Amenities:</h4>
            <div class="checkbox-group">
                <label><input type="checkbox" name="has_wifi"> WiFi</label>
                <label><input type="checkbox" name="has_private_pool"> Private Pool</label>
                <label><input type="checkbox" name="has_parking"> Parking</label>
                <label><input type="checkbox" name="has_balcony"> Balcony</label>
                <label><input type="checkbox" name="has_ev_station"> EV Charging</label>
                <label><input type="checkbox" name="allows_pets"> Pets Allowed</label>
                <label><input type="checkbox" name="allows_smoking"> Smoking Allowed</label>
            </div>

            <h4>Location:</h4>
            <div class="checkbox-group">
                <label><input type="checkbox" name="near_beach"> Near Beach</label>
                <label><input type="checkbox" name="near_lake"> Near Lake</label>
                <label><input type="checkbox" name="near_river"> Near River</label>
                <label><input type="checkbox" name="near_countryside"> Countryside</label>
                <label><input type="checkbox" name="near_city_center"> City Center</label>
            </div>

            <label for="distance_to_beach">Distance to Beach (km):</label>
            <input type="text" id="distance_to_beach" name="distance_to_beach">

            <label for="distance_to_lake">Distance to Lake (km):</label>
            <input type="text" id="distance_to_lake" name="distance_to_lake">

            <label for="distance_to_city_center">Distance to City Center (km):</label>
            <input type="text" id="distance_to_city_center" name="distance_to_city_center">

            <button type="submit">Add Property</button>
        </form>
    </div>
</div>

<footer>
    © 2025 Booking System | Designed by Nhi&Thuong ✨
</footer>
</body>
</html>
