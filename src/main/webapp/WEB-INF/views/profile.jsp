<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>My Profile | T-Kart</title>
    <link rel="stylesheet" href="css/profile.css" />
</head>
<body>

<div class="container">

    <!-- Profile Header -->
    <div class="profile-card">
        <h2>My Profile</h2>
        <div class="user-details">
            <p><span>Name:</span> ${user.name}</p>
            <p><span>Email:</span> ${user.email}</p>
            <p><span>Phone:</span> ${user.phone}</p>
        </div>
    </div>

    <!-- Orders Section -->
    <div class="orders-section">
        <h2>My Orders</h2>
        <c:choose>
            <c:when test="${empty ordersList}">
                <p class="no-orders">You haven't placed any orders yet.</p>
            </c:when>
            <c:otherwise>
                <c:forEach var="order" items="${ordersList}">
                    <div class="order-card">
                        <div class="order-header">
                            <h3>Order #${order.id}</h3>
                        </div>

                        <div class="products-grid">
                            <c:forEach var="product" items="${order.productDetailsList}">
                                <div class="product-card">
                                    <h4>${product.name}</h4>
                                    <p>Price: ${product.price}</p>
                                    <p>Quantity: ${product.quantity}</p>
                                    <p class="total">Total: ${product.totalPrice}</p>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
</div>

</body>
</html>
