<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Orders</title>
    <link href="${pageContext.request.contextPath}/css/myorders.css" rel="stylesheet">
</head>
<body>

<h2>My Orders</h2>

<c:choose>
    <c:when test="${empty ordersList}">
        <p>You have no orders.</p>
    </c:when>
    <c:otherwise>
        <c:forEach var="order" items="${ordersList}">
            <div class="order-card">
                <h3>Order #${order.id} - ${order.status}</h3>
                <p><b>Name:</b> ${order.name}</p>
                <p><b>Email:</b> ${order.email}</p>
                <p><b>Phone:</b> ${order.phoneNumber}</p>
                <p><b>City:</b> ${order.city}</p>
                <p><b>Address:</b> ${order.address}</p>
                <p><b>Payment Method:</b> ${order.paymentMethod}</p>

                <h4>Products:</h4>
                <ul>
                    <c:forEach var="pd" items="${order.productDetailsList}">
                        <li>${pd.name} - Qty: ${pd.quantity} - ₹${pd.totalPrice}</li>
                    </c:forEach>
                </ul>

                <p><b>Total Amount:</b> ₹
                    <c:set var="total" value="0"/>
                    <c:forEach var="pd" items="${order.productDetailsList}">
                        <c:set var="total" value="${total + pd.totalPrice}"/>
                    </c:forEach>
                    ${total}
                </p>

                <!-- Future: Cancel button -->
                <!-- <form action="cancelOrder" method="post">
                    <input type="hidden" name="orderId" value="${order.id}"/>
                    <button type="submit">Cancel Order</button>
                </form> -->
            </div>
            <hr>
        </c:forEach>
    </c:otherwise>
</c:choose>

</body>
</html>