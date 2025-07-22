<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.PropertyDTO" %>
<jsp:useBean id="property" scope="request" type="model.PropertyDTO" />
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Edit Property</title>
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

            html, body {
                overflow-x: hidden;
            }


            .main-content {
                flex: 1;
                padding: 60px 60px;
                width: 60%;
                max-width: unset;
                box-sizing: border-box;
                margin: 0 auto; /* ✅ canh giữa ngang */
            }



            h2 {
                text-align: center;
                color: #A53860;
                margin-bottom: 20px;
            }

            form {
                background: #ffffff;
                padding: 30px;
                border-radius: 12px;
                box-shadow: 0 8px 24px rgba(58, 5, 105, 0.08);
                border: 1px solid #f0d9e8;
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
                padding: 10px 12px;
                margin-top: 4px;
                border: 1px solid #ccc;
                border-radius: 8px;
                background-color: #fdfbff;
                transition: border-color 0.3s, box-shadow 0.3s;
            }

            input[type="text"]:focus,
            input[type="number"]:focus,
            textarea:focus {
                border-color: #EF88AD;
                box-shadow: 0 0 4px #EF88AD55;
                outline: none;
            }

            textarea {
                resize: vertical;
                min-height: 80px;
            }

            .checkbox-group {
                margin-top: 12px;
            }

            .checkbox-group label {
                margin-right: 12px;
                font-weight: normal;
                color: #333;
            }

            button[type="submit"] {
                margin-top: 20px;
                padding: 12px;
                width: 100%;
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
            <h2>Edit Property</h2>
            <form action="PropertyController" method="post">
                <input type="hidden" name="action" value="propertyUpdate" />
                <input type="hidden" name="id" value="${property.propertyId}" />

                <label>Name:</label>
                <input type="text" name="name" value="${property.name}" required>

                <label>Price per Night:</label>
                <input type="text" name="price_per_night" value="${property.pricePerNight}" required>

                <label>Location ID:</label>
                <input type="number" name="location_id" value="${property.locationId}" required>

                <label>Bedrooms:</label>
                <input type="number" name="num_bedrooms" value="${property.numBedrooms}" required>

                <label>Bathrooms:</label>
                <input type="number" name="num_bathrooms" value="${property.numBathrooms}" required>

                <label>Guests:</label>
                <input type="number" name="num_guests" value="${property.numGuests}" required>

                <label>Total Rooms:</label>
                <input type="number" name="num_rooms" value="${property.numRooms}" required>

                <label>Short Description:</label>
                <input type="text" name="short_description" value="${property.shortDescription}">

                <label>Detailed Description:</label>
                <textarea name="description">${property.description}</textarea>

                <label>Thumbnail URL:</label>
                <input type="text" name="thumbnail_url" value="${property.thumbnailUrl}">

                <h4>Amenities:</h4>
                <div class="checkbox-group">
                    <label><input type="checkbox" name="has_wifi" ${property.hasWifi ? "checked" : ""}/> WiFi</label>
                    <label><input type="checkbox" name="has_private_pool" ${property.hasPrivatePool ? "checked" : ""}/> Private Pool</label>
                    <label><input type="checkbox" name="has_parking" ${property.hasParking ? "checked" : ""}/> Parking</label>
                    <label><input type="checkbox" name="has_balcony" ${property.hasBalcony ? "checked" : ""}/> Balcony</label>
                    <label><input type="checkbox" name="has_ev_station" ${property.hasEvStation ? "checked" : ""}/> EV Charging</label>
                    <label><input type="checkbox" name="allows_pets" ${property.allowsPets ? "checked" : ""}/> Pets Allowed</label>
                    <label><input type="checkbox" name="allows_smoking" ${property.allowsSmoking ? "checked" : ""}/> Smoking Allowed</label>
                </div>

                <h4>Location Features:</h4>
                <div class="checkbox-group">
                    <label><input type="checkbox" name="near_beach" ${property.nearBeach ? "checked" : ""}/> Near Beach</label>
                    <label><input type="checkbox" name="near_lake" ${property.nearLake ? "checked" : ""}/> Near Lake</label>
                    <label><input type="checkbox" name="near_river" ${property.nearRiver ? "checked" : ""}/> Near River</label>
                    <label><input type="checkbox" name="near_countryside" ${property.nearCountryside ? "checked" : ""}/> Countryside</label>
                    <label><input type="checkbox" name="near_city_center" ${property.nearCityCenter ? "checked" : ""}/> City Center</label>
                </div>

                <label>Distance to Beach (km):</label>
                <input type="text" name="distance_to_beach" value="${property.distanceToBeach}">

                <label>Distance to Lake (km):</label>
                <input type="text" name="distance_to_lake" value="${property.distanceToLake}">

                <label>Distance to City Center (km):</label>
                <input type="text" name="distance_to_city_center" value="${property.distanceToCityCenter}">

                <button type="submit">Update</button>
            </form>
        </div>

        <footer>
            © 2025 Booking System | Designed by Nhi&Thuong ✨
        </footer>

    </body>
</html>
