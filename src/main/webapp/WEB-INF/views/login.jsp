<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link rel="stylesheet" type="text/css" href="css/register1.css">
</head>
<body>
    <div class="container">
        <h2>Login</h2>
        <form action="${pageContext.request.contextPath}/login" method="post">

            <label for="email">Email Address</label>
            <input type="email" id="email" name="email" placeholder="name@example.com" required />

            <label for="password">Password</label>
            <input type="password" id="password" name="password" required />

            <button type="submit">Login</button>

        </form>
        <div class="footer-link">
                 No account? <a href="register">Register</a>
        </div>
        <a href="products" style="display:inline-block; font-family:'Bebas Neue',sans-serif; font-size:1rem; letter-spacing:0.1em; color:#e8ff47; background:rgba(232,255,71,0.12); border:1px solid rgba(232,255,71,0.2); padding:0.35rem 0.9rem; border-radius:8px; text-decoration:none; text-shadow:0 0 12px rgba(232,255,71,0.3); transition:all 0.25s ease;">Home</a>
        <%
    String errorMsg = (String) request.getAttribute("error");
    if (errorMsg != null && !errorMsg.isEmpty()) {
    %>
        <p style="color:red;"><%= errorMsg %></p>
    <%
    }
    %>

   <c:if test="${not empty message}">
       <p style="color:green;">
           ${message}
       </p>
   </c:if>
</body>
</html>