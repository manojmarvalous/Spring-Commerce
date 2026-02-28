<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Product</title>
    <link href="${pageContext.request.contextPath}/css/admin.css" rel="stylesheet">
</head>
<body>

<div class="admin-container">
    <h2>Add New Product</h2>

    <form action="${pageContext.request.contextPath}/admin/save-product"
          method="post" enctype="multipart/form-data">

        <label>Product Name</label>
        <input type="text" name="name" required>

        <label>Title</label>
        <input type="text" name="title">

        <label>Description</label>
        <textarea name="description"></textarea>

        <label>Price</label>
        <input type="number" step="0.01" name="price" required>

        <label>Stock</label>
        <input type="number" name="stock" required>

        <label>Category</label>
        <input type="text" name="category">

        <label>Product Image</label>
        <input type="file" name="imageFile" required>

        <button type="submit">Save Product</button>
    </form>
</div>
<a href="${pageContext.request.contextPath}/products" class="home-btn">
    Home
</a>
</body>
</html>