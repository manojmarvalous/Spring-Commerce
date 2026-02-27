<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Cart</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/cart.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

</head>

<body>

<main>
    <h2>Cart Products</h2>
    <br>

<c:choose>
    <c:when test="${empty cartProducts}">
        <h3 class="empty-cart">Your Cart Is Empty</h3>
    </c:when>

    <c:otherwise>
    <div class="products">

        <c:forEach var="item" items="${cartProducts}">
            <div class="product-card">

                <img src="${item.product.imageUrl}" alt="${item.product.name}">

                <h3>${item.product.name}</h3>
                <p>${item.product.title}</p>

                <p class="price">₹ ${item.product.price}</p>

                <!-- Quantity Controls -->
                <div class="quantity-section">

                    <!-- Decrease -->
                    <form action="${pageContext.request.contextPath}/cart/updateQuantity" method="post">
                        <input type="hidden" name="cartProductId" value="${item.id}" />
                        <input type="hidden" name="quantity" value="${item.quantity - 1}" />
                        <button type="submit" class="qty-btn">
                            <i class="fas fa-minus"></i>
                        </button>
                    </form>

                    <span class="quantity-value">${item.quantity}</span>

                    <!-- Increase -->
                    <form action="${pageContext.request.contextPath}/cart/updateQuantity" method="post">
                        <input type="hidden" name="cartProductId" value="${item.id}" />
                        <input type="hidden" name="quantity" value="${item.quantity + 1}" />
                        <button type="submit" class="qty-btn">
                            <i class="fas fa-plus"></i>
                        </button>
                    </form>

                </div>

                <!-- Delete -->
                <form action="${pageContext.request.contextPath}/cart/remove" method="post">
                    <input type="hidden" name="cartProductId" value="${item.id}" />
                    <button type="submit" class="delete-btn">
                        <i class="fas fa-trash-alt"></i> Remove
                    </button>
                </form>

            </div>
        </c:forEach>

    </div>

    <!-- Checkout Button -->
    <div class="checkout-container">
        <a href="${pageContext.request.contextPath}/checkout" class="checkout-btn">
            <i class="fas fa-shopping-cart"></i> Proceed to Checkout
        </a>
    </div>

    </c:otherwise>
</c:choose>

<br>

<a href="${pageContext.request.contextPath}/products" class="home-btn">
    Home
</a>

</main>

</body>
</html>