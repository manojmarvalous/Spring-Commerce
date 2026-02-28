<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Manage Products</title>
    <link href="${pageContext.request.contextPath}/css/admin.css" rel="stylesheet">
</head>
<body>

<div class="admin-container">

    <h2>All Products</h2>
    <a href="${pageContext.request.contextPath}/admin/add-product" class="add-btn">+ Add Product</a>

    <c:if test="${param.success != null}">
        <div class="success-msg">Product added successfully!</div>
    </c:if>

    <table>
        <tr>
            <th>Image</th>
            <th>Name</th>
            <th>Price</th>
            <th>Stock</th>
            <th>Action</th>
        </tr>

        <c:forEach var="p" items="${products}">
            <tr>
                <td>
                    <img src="${pageContext.request.contextPath}/uploads/products/${p.imageUrl}"
                         width="60">
                </td>
                <td>${p.name}</td>
                <td>₹${p.price}</td>
                <td>${p.stock}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/admin/delete-product/${p.id}"
                       class="delete-btn">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>

</div>

<a href="${pageContext.request.contextPath}/products" class="home-btn">
    Home
</a>

</body>
</html>