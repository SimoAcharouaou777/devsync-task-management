<%--
  Created by IntelliJ IDEA.
  User: Youcode
  Date: 10/1/2024
  Time: 5:03 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Signup</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .ezy__signin1 {
            --bs-body-color: #333b7b;
            --bs-body-bg: rgb(255, 255, 255);
            --ezy-theme-color: rgb(13, 110, 253);
            --ezy-theme-color-rgb: 13, 110, 253;
            --ezy-form-card-bg: #ffffff;
            --ezy-form-card-shadow: 0 16px 24px rgba(197, 206, 222, 0.25);
            background-color: var(--bs-body-bg);
        }
        .ezy__signin1-heading {
            font-weight: bold;
            font-size: 25px;
            line-height: 25px;
            color: var(--bs-body-color);
        }
        .ezy__signin1-form-card {
            background-color: var(--ezy-form-card-bg);
            border: none;
            box-shadow: var(--ezy-form-card-shadow);
            border-radius: 15px;
        }
        .ezy__signin1-form-card * {
            color: var(--bs-body-color);
        }
        .ezy__signin1 .form-control {
            min-height: 48px;
            line-height: 40px;
            border-color: transparent;
            background: rgba(163, 190, 241, 0.14);
            border-radius: 10px;
            color: var(--bs-body-color);
        }
        .ezy__signin1 .form-control:focus {
            border-color: var(--ezy-theme-color);
            box-shadow: none;
        }
        .ezy__signin1-btn-submit {
            padding: 12px 30px;
            background-color: #333b7b;
            color: #ffffff;
        }
        .ezy__signin1-btn-submit:hover {
            color: #ffffff;
        }
        .ezy__signin1-btn {
            padding: 12px 30px;
        }
        .ezy__signin1-btn,
        .ezy__signin1-btn * {
            color: #ffffff;
        }
        .ezy__signin1-btn:hover {
            color: #ffffff;
        }
        .ezy__signin1-or-separator {
            position: relative;
        }
        .ezy__signin1-or-separator hr {
            border-color: var(--bs-body-color);
            opacity: 0.15;
        }
        .ezy__signin1-or-separator span {
            background-color: var(--ezy-form-card-bg);
            color: var (--bs-body-color);
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate3d(-50%, -50%, 0);
            opacity: 0.8;
        }
    </style>
</head>
<body>
<section class="ezy__signin1 light d-flex align-items-center">
    <div class="container">
        <div class="row py-4 justify-content-center">
            <div class="col-lg-5">
                <div class="card ezy__signin1-form-card">
                    <div class="card-body p-md-5">
                        <h2 class="ezy__signin1-heading mb-4 mb-md-5">Signup</h2>
                        <form action="${pageContext.request.contextPath}/signup" method="post">
                            <div class="form-group mb-4 mt-2">
                                <label for="username" class="mb-2">Username</label>
                                <input type="text" class="form-control" id="username" name="username" placeholder="Enter Username" required />
                            </div>
                            <div class="form-group mb-2 mt-2">
                                <label for="password" class="mb-2">Password</label>
                                <input type="password" class="form-control" id="password" name="password" placeholder="Enter Password" required />
                            </div>
                            <div class="form-group mb-2 mt-2">
                                <label for="firstName" class="mb-2">First Name</label>
                                <input type="text" class="form-control" id="firstName" name="firstName" placeholder="Enter First Name" required />
                            </div>
                            <div class="form-group mb-2 mt-2">
                                <label for="lastName" class="mb-2">Last Name</label>
                                <input type="text" class="form-control" id="lastName" name="lastName" placeholder="Enter Last Name" required />
                            </div>
                            <div class="form-group mb-2 mt-2">
                                <label for="email" class="mb-2">Email</label>
                                <input type="email" class="form-control" id="email" name="email" placeholder="Enter Email" required />
                            </div>
                            <button type="submit" class="btn ezy__signin1-btn-submit w-100">Register</button>
                            <div class="text-center mt-4 mt-md-5">
                                <p class="mb-0 opacity-50 lh-1">Already have an account?</p>
                                <a href="#" class="btn btn-link py-0 text-dark text-decoration-none">Log In</a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Bootstrap JS and dependencies -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>