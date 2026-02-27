<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>T-Kart</title>
     <link href="https://fonts.googleapis.com/css2?family=Bebas+Neue&family=DM+Sans:wght@300;400;500;600&family=DM+Mono:wght@400&display=swap" rel="stylesheet">
   <link rel="stylesheet"
         href="${pageContext.request.contextPath}/css/register1.css" />
</head>
<body>

<div class="container">
    <h2>Create Your Account</h2>
  
    <form action="${pageContext.request.contextPath}/register" method="post">

        <label for="fullname">Full Name</label>
        <input type="text" name="name" required />

        <label for="email">Email Address</label>
        <input type="email" id="email" name="email" placeholder="name@example.com" required />

        <label for="password">Password</label>
        <input type="password" id="password" name="password" required />

        <label for="phone">Phone Number</label>
        <input type="number" id="phone" name="phone"  />
        
        <label for="address">Address</label>
        <input type="text" id="address" name="address" required/>
        

        <button type="submit">Sign Up</button>

    </form>

    <div class="footer-link">
        Already have an account? <a href="login">Login here</a>

    </div>
    <a href="products" style="display:inline-block; font-family:'Bebas Neue',sans-serif; font-size:1rem; letter-spacing:0.1em; color:#e8ff47; background:rgba(232,255,71,0.12); border:1px solid rgba(232,255,71,0.2); padding:0.35rem 0.9rem; border-radius:8px; text-decoration:none; text-shadow:0 0 12px rgba(232,255,71,0.3); transition:all 0.25s ease;">Home</a>

<c:if test="${not empty error}" >
<p style="color : red ; font-size : 15px">${error}
</p>
</c:if>

</div>

</body>
</html>
