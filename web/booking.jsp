<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Booking Confirmation</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f2f5;
            margin: 20px;
        }

        .container {
            max-width: 700px;
            margin: auto;
            background: #ffffff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 8px 16px rgba(0,0,0,0.1);
        }

        h2 {
            color: #2c3e50;
            margin-bottom: 25px;
            border-bottom: 2px solid #eee;
            padding-bottom: 10px;
        }

        .info p, .price-box p {
            margin: 8px 0;
            font-size: 16px;
            color: #333;
        }

        .bold {
            font-weight: bold;
            color: #2c3e50;
        }

        .price-box {
            background-color: #f8f8f8;
            padding: 15px;
            margin-top: 20px;
            border-radius: 8px;
            border: 1px solid #ddd;
        }

        .confirm-button {
            padding: 12px 24px;
            background: #27ae60;
            color: white;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-weight: bold;
            font-size: 16px;
            transition: background 0.3s ease;
        }

        .confirm-button:hover {
            background: #219150;
        }

        .back-link {
            display: inline-block;
            margin-top: 20px;
            text-decoration: none;
            color: #3498db;
            font-size: 14px;
        }

        .success-message {
            color: #27ae60;
            font-weight: bold;
            margin-top: 20px;
            font-size: 16px;
        }

        .error-message {
            color: #e74c3c;
            font-weight: bold;
            margin-top: 20px;
            font-size: 16px;
        }

        .payment-button {
            background-color: #3498db;
            margin-top: 15px;
        }

        .payment-button:hover {
            background-color: #2980b9;
        }

        h3 {
            margin-top: 40px;
            text-align: center;
            color: #333;
        }

        ul.house-rules {
            max-width: 700px;
            margin: 15px auto;
            list-style-type: circle;
            padding-left: 20px;
            color: #555;
        }

        ul.house-rules li {
            margin-bottom: 8px;
        }
    </style>
</head>
<body>

<div class="container">
    <h2>📑 Booking Confirmation</h2>

    <c:if test="${not empty property}">
        <div class="info">
            <p><span class="bold">🏠 Property name:</span> ${property.name}</p>
            <p><span class="bold">🛏️ Number of guests:</span> ${numGuests}</p>
            <p><span class="bold">📅 Check-in:</span> ${checkInFormatted}</p>
            <p><span class="bold">📅 Check-out:</span> ${checkOutFormatted}</p>
            <p><span class="bold">💵 Price per night:</span> $<fmt:formatNumber value="${pricePerNight}" pattern="#,##0.00"/></p>
        </div>

        <div class="price-box">
            <p><span class="bold">🧾 Total:</span> $<fmt:formatNumber value="${totalPrice}" pattern="#,##0.00"/></p>
            <p style="font-size:13px; color:#777;">(Including taxes and service fees)</p>
        </div>

        <form action="${pageContext.request.contextPath}/BookingController" method="post" style="margin-top:25px;">
            <input type="hidden" name="action" value="confirmBooking"/>
            <input type="hidden" name="propertyId" value="${property.propertyId}"/>
            <input type="hidden" name="pricePerNight" value="${pricePerNight}"/>
            <input type="hidden" name="checkIn" value="${checkIn}"/>
            <input type="hidden" name="checkOut" value="${checkOut}"/>
            <input type="hidden" name="numGuests" value="${numGuests}"/>
            <button type="submit" class="confirm-button">✅ Confirm Booking</button>
        </form>

        <c:if test="${not empty bookingSuccess}">
            <c:choose>
                <c:when test="${bookingSuccess eq true}">
                    <p class="success-message">🎉 Booking successful!</p>
                    <form action="payment.jsp" method="get">
                        <input type="hidden" name="bookingId" value="${bookingId}"/>
                        <input type="hidden" name="amount" value="${totalPrice}"/>
                        <button type="submit" class="confirm-button payment-button">💳 Proceed to Payment</button>
                    </form>
                </c:when>
                <c:otherwise>
                    <p class="error-message">❌ Booking failed. Please try again.</p>
                </c:otherwise>
            </c:choose>
        </c:if>

        <a href="${pageContext.request.contextPath}/MainController?action=propertyDetail&propertyId=${property.propertyId}" class="back-link">
            ⬅️ Back to property details
        </a>
    </c:if>

    <c:if test="${empty property}">
        <p class="error-message">Property information not found.</p>
        <a href="${pageContext.request.contextPath}/MainController?action=propertyList" class="back-link">⬅️ Back to list</a>
    </c:if>
</div>

<!-- House Rules -->
<h3>📜 House Rules</h3>
<ul class="house-rules">
    <c:forEach var="r" items="${houseRules}">
        <li>${r.ruleText}</li>
    </c:forEach>
</ul>

</body>
</html>
