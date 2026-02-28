<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>My Profile</title>
    <link href="${pageContext.request.contextPath}/css/profile.css" rel="stylesheet">
</head>
<body>

<div class="profile-container">

    <div class="profile-header">
        <h2>My Profile</h2>
        <button onclick="enableEdit()" class="edit-btn">Update</button>
    </div>

    <c:if test="${param.success != null}">
        <div class="success-msg">Profile updated successfully!</div>
    </c:if>

    <div class="profile-card">

        <!-- Profile Image -->
        <div class="profile-image-section">
            <img id="preview"
                 src="${pageContext.request.contextPath}/uploads/${user.profileImage}"
                 onerror="this.src='${pageContext.request.contextPath}/images/default.jpg'"
                 class="profile-img">

        </div>

        <form action="updateProfile" method="post" enctype="multipart/form-data">

            <input type="file" name="imageFile" id="imageFile"
                   onchange="previewImage(event)" style="display:none;">

            <button type="button" onclick="document.getElementById('imageFile').click()"
                    class="change-photo-btn" style="display:none;" id="photoBtn">
                Change Photo
            </button>

            <label>Name</label>
            <input type="text" name="name" value="${user.name}" disabled id="name">

            <label>Email</label>
            <input type="email" name="email" value="${user.email}" disabled id="email">

            <label>Phone</label>
            <input type="text" name="phone" value="${user.phone}" disabled id="phone">

            <label>Address</label>
            <textarea name="address" disabled id="address">${user.address}</textarea>

            <button type="submit" class="save-btn" style="display:none;" id="saveBtn">
                Save Changes
            </button>

        </form>

        <div class="stats">
            <div>
                <h3>${cartCount}</h3>
                <p>Cart Items</p>
            </div>
            <div>
                <h3>${orderCount}</h3>
                <p>Total Orders</p>
            </div>
        </div>

    </div>
</div>

<c:if test="${not empty error}">
    <div class="error-msg">${error}</div>
</c:if>

<script>
function enableEdit() {
    document.getElementById("name").disabled = false;
    document.getElementById("email").disabled = false;
    document.getElementById("phone").disabled = false;
    document.getElementById("address").disabled = false;

    document.getElementById("saveBtn").style.display = "block";
    document.getElementById("photoBtn").style.display = "inline-block";
}

function previewImage(event) {
    const reader = new FileReader();
    reader.onload = function(){
        document.getElementById('preview').src = reader.result;
    }
    reader.readAsDataURL(event.target.files[0]);
}
</script>

<a href="${pageContext.request.contextPath}/products" class="home-btn">
    Home
</a>

</body>
</html>