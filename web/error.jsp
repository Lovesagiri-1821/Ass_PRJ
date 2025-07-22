<%-- 
    Document   : error.jsp
    Created on : Jul 14, 2025, 12:53:49 PM
    Author     : Admin
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head><title>Danh sách Property</title></head>
<body>
<h2>Danh sách Property</h2>
<a href="property-add.jsp">➕ Thêm Property</a>

<c:if test="${empty properties}">
    <p>Không có property nào.</p>
</c:if>

<c:forEach var="p" items="${properties}">
    <div style="border:1px solid #aaa; padding:10px; margin:10px;">
        <h3>${p.name}</h3>
        <p>${p.description}</p>
        <p>Giá/đêm: ${p.pricePerNight}</p>
        <a href="PropertyController?action=propertyEdit&id=${p.propertyId}">✏️ Sửa</a> |
        <a href="PropertyController?action=propertyDelete&id=${p.propertyId}" onclick="return confirm('Bạn có chắc muốn xoá?');">🗑️ Xoá</a>
    </div>
</c:forEach>
</body>
</html>

