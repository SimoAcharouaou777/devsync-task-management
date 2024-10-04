<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Welcome</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            background-color: #f8f9fa;
        }
        .welcome-container {
            text-align: center;
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        .welcome-container h2 {
            margin-bottom: 20px;
        }
        .welcome-container .btn {
            margin: 5px;
        }
    </style>
</head>
<body>
<div class="welcome-container">
    <h2>Welcome to Our Website!</h2>
    <p>We're glad to have you here. Please login or sign up to continue.</p>
    <a href="${pageContext.request.contextPath}/signin" class="btn btn-primary">Login</a>
    <a href="${pageContext.request.contextPath}/signup" class="btn btn-secondary">Sign Up</a>
</div>
<!-- Bootstrap JS and dependencies -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>