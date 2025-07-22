<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Property Details</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 20px;
                background-color: #f5f5f5;
                color: #333;
            }
            .container {
                max-width: 1000px;
                margin: 0 auto;
                background-color: #fff;
                padding: 30px;
                border-radius: 8px;
                box-shadow: 0 0 15px rgba(0,0,0,0.1);
                display: flex;
                gap: 30px;
            }
            .property-details {
                flex: 2;
            }
            .booking-section {
                flex: 1;
                min-width: 300px;
            }
            h2, h3 {
                color: #333;
                border-bottom: 2px solid #eee;
                padding-bottom: 10px;
                margin-bottom: 20px;
            }
            .label {
                font-weight: bold;
                color: #555;
            }
            ul {
                list-style: none;
                padding: 0;
                margin: 5px 0 15px 0;
            }
            ul li {
                margin-bottom: 5px;
                color: #666;
            }
            img {
                display: block;
                margin-top: 20px;
                border-radius: 8px;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
                max-width: 100%;
            }
            .message {
                color: green;
                font-weight: bold;
                margin-bottom: 15px;
            }
            .error-message {
                color: red;
                font-weight: bold;
                margin-bottom: 15px;
            }
            img.thumbnail {
                display: block;
                margin: 0 auto 20px auto;
                border-radius: 8px;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
                width: 250px;
                height: 250px;
                object-fit: cover;
            }


        </style>
        <script>
            function validateBookingForm() {
                const checkIn = document.getElementById("checkIn").value;
                const checkOut = document.getElementById("checkOut").value;
                const guests = document.getElementById("numGuests").value;
                if (!checkIn || !checkOut || !guests) {
                    alert("❗ Please select check-in, check-out dates and number of guests.");
                    return false;
                }
                return true;
            }
        </script>
    </head>
    <body>

        <c:if test="${empty property and not empty requestScope.property}">
            <c:set var="property" value="${requestScope.property}" />
        </c:if>

        <div class="container">
            <!-- Property Details -->
            <div class="property-details">
                <h2>🏠 Property Details</h2>

                <c:if test="${not empty sessionScope.flashMessage}">
                    <p class="message">${sessionScope.flashMessage}</p>
                    <c:remove var="flashMessage" scope="session"/>
                </c:if>

                <c:if test="${not empty property}">
                    <img src="${property.thumbnailUrl}" alt="Thumbnail" class="thumbnail">
                    <p><i>${property.shortDescription}</i></p>
                    <p><span class="label">Name:</span> ${property.name}</p>
                    <p><span class="label">Description:</span> ${property.description}</p>
                    <p><span class="label">Price/Night:</span> $<fmt:formatNumber value="${property.pricePerNight}" pattern="#,##0.00"/></p>
                    <p><span class="label">Rooms:</span> ${property.numRooms}</p>
                    <p><span class="label">Bedrooms:</span> ${property.numBedrooms}</p>
                    <p><span class="label">Bathrooms:</span> ${property.numBathrooms}</p>
                    <p><span class="label">Capacity:</span> ${property.numGuests} guests</p>

                    <p><span class="label">Amenities:</span></p>
                    <ul>
                        <c:if test="${property.hasWifi}"><li>📶 Wi-Fi</li></c:if>
                        <c:if test="${property.hasPrivatePool}"><li>🏊 Private Pool</li></c:if>
                        <c:if test="${property.hasBalcony}"><li>🌇 Balcony</li></c:if>
                        <c:if test="${property.hasParking}"><li>🚗 Parking</li></c:if>
                        <c:if test="${property.allowsPets}"><li>🐶 Pet Friendly</li></c:if>
                        <c:if test="${property.allowsSmoking}"><li>🚬 Smoking Allowed</li></c:if>
                        <c:if test="${property.hasEvStation}"><li>🔌 EV Charging Station</li></c:if>
                        </ul>

                        <p><span class="label">Location:</span></p>
                        <ul>
                        <c:if test="${property.nearLake}"><li>🌅 Near Lake</li></c:if>
                        <c:if test="${property.nearBeach}"><li>🌊 Near Beach</li></c:if>
                        <c:if test="${property.nearRiver}"><li>🌉 Near River</li></c:if>
                        <c:if test="${property.nearCityCenter}"><li>🏙️ Near City Center</li></c:if>
                        <c:if test="${property.nearCountryside}"><li>🌄 Near Countryside</li></c:if>
                        </ul>

                        <p><span class="label">Distance:</span></p>
                        <ul>
                            <li>To Beach: ${property.distanceToBeach} km</li>
                        <li>To Lake: ${property.distanceToLake} km</li>
                        <li>To City Center: ${property.distanceToCityCenter} km</li>
                    </ul>



                    <h3>🖼️ Manage Images</h3>
                    <form action="${pageContext.request.contextPath}/ImageController" method="get" style="display:inline;">
                        <input type="hidden" name="action" value="view"/>
                        <input type="hidden" name="propertyId" value="${property.propertyId}"/>
                        <button type="submit"
                                style="background:#007bff; color:white; border:none; padding:4px 8px; border-radius:4px; cursor:pointer;">
                            🖼️ View Images
                        </button>
                    </form>

                    <h3>📣 User Reviews</h3>

                    <c:if test="${sessionScope.user != null}">
                        <form action="${pageContext.request.contextPath}/ReviewController" method="post" style="margin-bottom: 20px;">
                            <input type="hidden" name="action" value="addReview"/>
                            <input type="hidden" name="propertyId" value="${property.propertyId}"/>

                            <label>⭐ Rating:</label>
                            <input type="number" name="rating" min="1" max="5" required style="width: 50px; margin-right: 10px;"/>

                            <label>💬 Comment:</label>
                            <input type="text" name="comment" required style="width: 200px; margin-right: 10px;"/>

                            <button type="submit"
                                    style="background-color: #28a745; color:white; border:none; padding:4px 8px; border-radius:4px; cursor:pointer;">
                                ➕ Submit Review
                            </button>
                        </form>
                    </c:if>

                    <c:choose>
                        <c:when test="${not empty reviews}">
                            <ul>
                                <c:forEach var="review" items="${reviews}">
                                    <li style="margin-bottom: 15px; border-bottom: 1px solid #ccc; padding-bottom: 10px;">
                                        <strong>⭐ Rating:</strong> ${review.rating}/5<br/>
                                        <strong>💬 Comment:</strong> ${review.comment}<br/>
                                        <strong>📅 Date:</strong>
                                        <fmt:formatDate value="${review.createdAt}" pattern="dd/MM/yyyy" />

                                        <!-- Edit Button -->
                                        <form action="${pageContext.request.contextPath}/ReviewController" method="get" style="display:inline;">
                                            <input type="hidden" name="action" value="editForm"/>
                                            <input type="hidden" name="reviewId" value="${review.reviewId}"/>
                                            <button type="submit"
                                                    style="margin-left:10px; background-color: #ffc107; border:none; color:white; padding:3px 8px; border-radius:3px; cursor:pointer;">
                                                ✏️ Edit
                                            </button>
                                        </form>

                                        <!-- Delete Button -->
                                        <form action="${pageContext.request.contextPath}/ReviewController" method="post" style="display:inline;"
                                              onsubmit="return confirm('Are you sure you want to delete this review?');">
                                            <input type="hidden" name="action" value="deleteReview"/>
                                            <input type="hidden" name="reviewId" value="${review.reviewId}"/>
                                            <input type="hidden" name="propertyId" value="${property.propertyId}"/> 
                                            <button type="submit"
                                                    style="background-color: #dc3545; border:none; color:white; padding:3px 8px; border-radius:3px; cursor:pointer;">
                                                🗑️ Delete
                                            </button>
                                        </form>
                                    </li>
                                </c:forEach>
                            </ul>
                        </c:when>
                        <c:otherwise>
                            <p style="color: #999;">No reviews available for this property yet.</p>
                        </c:otherwise>
                    </c:choose>
                </c:if>

                <c:if test="${empty property}">
                    <p class="error-message">Property information not found.</p>
                </c:if>
            </div>

            <!-- Booking Section -->
            <div class="booking-section">
                <div style="background-color: #111; color: white; padding: 20px; border-radius: 12px;">
                    <div style="border: 1px solid #2ecc71; background-color: #1e1e1e; padding: 10px; border-radius: 8px; margin-bottom: 15px;">
                        <span style="color: #2ecc71;">✔ Selected dates are available</span>
                    </div>

                    <form action="${pageContext.request.contextPath}/BookingController" method="get" onsubmit="return validateBookingForm()">
                        <input type="hidden" name="action" value="bookForm" />
                        <input type="hidden" name="propertyId" value="${property.propertyId}" />
                        <input type="hidden" name="pricePerNight" value="${property.pricePerNight}" />

                        <label for="checkIn" style="color: #ccc;">Check-in:</label>
                        <input type="text" id="checkIn" name="checkIn" required style="width: 100%; padding: 8px; margin-bottom: 10px;"/>

                        <label for="checkOut" style="color: #ccc;">Check-out:</label>
                        <input type="text" id="checkOut" name="checkOut" required style="width: 100%; padding: 8px; margin-bottom: 10px;"/>

                        <label for="numGuests" style="color: #ccc;">Guests:</label>
                        <input type="number" id="numGuests" name="numGuests" min="1" max="${property.numGuests}" value="1" required
                               style="width: 100%; padding: 8px; margin-bottom: 20px;"/>

                        <button type="submit"
                                style="width: 100%; padding: 12px; background-color: #c00; color: white; font-weight: bold; border: none; border-radius: 6px; cursor: pointer;">
                            Book now
                        </button>
                    </form>

                    <p style="font-size: 12px; color: #aaa; margin-top: 10px;">Don't worry – you won't be charged when clicking this button</p>
                    <div style="margin-top: 15px; font-size: 14px; color: #ccc;">
                        $${property.pricePerNight} × number of nights will be calculated in the next step
                        <div style="text-align: right; font-weight: bold; font-size: 18px; color: #2ecc71;">
                            Total price: To be calculated
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <c:if test="${bookingCancelled == true}">
            <p class="error-message">❌ You cancelled the booking. Reservation was not completed.</p>
        </c:if>

        <p style="text-align: center; margin-top: 30px;">
            <a href="MainController?action=propertyList">⬅️ Back to Property List</a>
        </p>

        <!-- Flatpickr CSS & JS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
        <script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>

        <script>
                        document.addEventListener("DOMContentLoaded", function () {
                            const unavailableDates = [
            <c:forEach var="d" items="${unavailableDates}" varStatus="status">
                            "${d}"<c:if test="${!status.last}">,</c:if>
            </c:forEach>
                            ];

                            flatpickr("#checkIn", {
                                disable: unavailableDates,
                                dateFormat: "Y-m-d",
                                minDate: "today"
                            });

                            flatpickr("#checkOut", {
                                disable: unavailableDates,
                                dateFormat: "Y-m-d",
                                minDate: "today"
                            });
                        });
        </script>

    </body>
</html>
