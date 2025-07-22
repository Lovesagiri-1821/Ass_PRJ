<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Hóa đơn đặt phòng</title>
    <style>
        body { font-family: Arial, sans-serif; background:#f7f7f7; margin:20px; }
        .container {
            max-width:700px; margin:auto; background:white; padding:25px;
            border-radius:8px; box-shadow:0 0 10px rgba(0,0,0,0.1);
        }
        .bold { font-weight:bold; }
        .section { margin-bottom:20px; }
        .button {
            padding:10px 20px; border:none; border-radius:5px; cursor:pointer; font-weight:bold;
        }
        .cancel-button { background:#e74c3c; color:white; }
        .back-link {
            display:inline-block; margin-top:15px; text-decoration:none; color:#3498db;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>🧾 Hóa đơn đặt phòng</h2>

    <c:if test="${not empty booking}">
        <div class="section">
            <p><span class="bold">Mã đặt phòng:</span> ${booking.bookingId}</p>
            <p><span class="bold">Tên property:</span> ${booking.property.name}</p>
            <p><span class="bold">Số khách:</span> ${booking.numGuests}</p>
            <p><span class="bold">Check-in:</span> ${booking.checkInFormatted}</p>
            <p><span class="bold">Check-out:</span> ${booking.checkOutFormatted}</p>
            <p><span class="bold">Giá mỗi đêm:</span> $<fmt:formatNumber value="${booking.pricePerNight}" pattern="#,##0.00"/></p>
        </div>

        <div class="section">
            <p><span class="bold">Tổng cộng:</span> $<fmt:formatNumber value="${booking.totalPrice}" pattern="#,##0.00"/></p>
            <p style="font-size:13px; color:#666;">(Đã bao gồm thuế và phí dịch vụ)</p>
        </div>

        <div class="section">
            <p><span class="bold">Trạng thái hiện tại:</span> 
                <c:choose>
                    <c:when test="${booking.status eq 'canceled'}">❌ Đã hủy</c:when>
                    <c:otherwise>✅ Đã xác nhận</c:otherwise>
                </c:choose>
            </p>
        </div>

        <c:if test="${booking.status ne 'canceled'}">
            <form action="${pageContext.request.contextPath}/BookingController" method="post">
                <input type="hidden" name="action" value="cancelBooking"/>
                <input type="hidden" name="bookingId" value="${booking.bookingId}"/>
                <button type="submit" class="button cancel-button">❌ Hủy đặt phòng</button>
            </form>
        </c:if>

        <a href="${pageContext.request.contextPath}/MainController?action=propertyDetail&propertyId=${booking.property.propertyId}" class="back-link">
            ⬅️ Xem lại chi tiết property
        </a>
    </c:if>

    <c:if test="${empty booking}">
        <p style="color:red;">Không tìm thấy thông tin đặt phòng.</p>
        <a href="${pageContext.request.contextPath}/MainController?action=propertyList" class="back-link">⬅️ Quay lại danh sách</a>
    </c:if>
</div>
</body>
</html>
