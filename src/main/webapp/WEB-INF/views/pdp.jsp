<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Product Details</title>
    <link href="${pageContext.request.contextPath}/css/pdp.css" rel="stylesheet">
</head>
<body>

    <div class="product-details">
        <img src="${product.imageUrl}" width="300px">

        <h2>${product.name}</h2>

        <p>${product.description}</p>

        <h3>&#8377;${product.price}</h3>

        <form action="${pageContext.request.contextPath}/cart/add/${product.id}" method="post">
            <button type="submit">Add to Cart</button>
        </form>

        <br>
        <a href="${pageContext.request.contextPath}/products">⬅ Back to Products</a>
    </div>

</body>
</html>