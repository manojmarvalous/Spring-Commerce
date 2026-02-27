<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Order Confirmation</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/order-confirmation.css">
</head>
<body>

<div class="container">
    <h2>Thank you for your order!</h2>
    <p>Order placed successfully. Here are the details:</p>

    <div class="order-summary">
        <h3>Customer Info</h3>
        <p><b>Name:</b> ${order.user.name}</p>
        <p><b>Email:</b> ${order.user.email}</p>
        <p><b>Phone:</b> ${order.user.phone}</p>

        <h3>Shipping Information</h3>
        <p><b>Address:</b> ${order.address}, ${order.city}, ${order.country} - ${order.postalCode}</p>
        <p><b>Payment Method:</b> ${order.paymentMethod}</p>

        <h3>Products Ordered</h3>
        <c:forEach var="pd" items="${order.productDetailsList}">
            <div class="order-item">
                <strong>${pd.name}</strong> — Qty: ${pd.quantity} × ₹${pd.price} = ₹${pd.totalPrice}
            </div>
        </c:forEach>

        <hr>
        <p><b>Grand Total:</b>
            ₹<c:set var="grandTotal" value="0" />
            <c:forEach var="pd" items="${order.productDetailsList}">
                <c:set var="grandTotal" value="${grandTotal + pd.totalPrice}" />
            </c:forEach>
            ${grandTotal}
        </p>
    </div>

    <a href="${pageContext.request.contextPath}/products" class="home-btn">Continue Shopping</a>
</div>

</body>
</html>