<%--
  Created by IntelliJ IDEA.
  User: Youcode
  Date: 10/8/2024
  Time: 3:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Manager - Change Requests</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            height: 100vh;
            display: flex;
            background-color: #f8f9fa;
        }
        .sidebar {
            width: 250px;
            background-color: #343a40;
            color: white;
            padding: 15px;
            height: 100vh;
            position: fixed;
        }
        .sidebar a {
            color: white;
            text-decoration: none;
            display: block;
            padding: 10px;
            margin: 5px 0;
        }
        .sidebar a:hover {
            background-color: #495057;
        }
        .main-content {
            margin-left: 250px;
            padding: 20px;
            width: calc(100% - 250px);
        }
        .ezy__signin1-heading {
            font-weight: bold;
            font-size: 25px;
            line-height: 25px;
            color: #333b7b;
        }
        .ezy__signin1-form-card {
            background-color: #ffffff;
            border: none;
            box-shadow: 0 16px 24px rgba(197, 206, 222, 0.25);
            border-radius: 15px;
        }
        .ezy__signin1-form-card * {
            color: #333b7b;
        }
        .request-list {
            margin-top: 30px;
        }
        .request-item {
            background-color: white;
            box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
            padding: 15px;
            margin-bottom: 15px;
            border-radius: 8px;
        }
        .form-control {
            min-height: 48px;
            line-height: 40px;
            border-color: transparent;
            background: rgba(163, 190, 241, 0.14);
            border-radius: 10px;
            color: #333b7b;
        }
        .form-control:focus {
            border-color: rgb(13, 110, 253);
            box-shadow: none;
        }
        .btn-primary {
            padding: 12px 30px;
            background-color: #333b7b;
            color: #ffffff;
        }
        .btn-primary:hover {
            color: #ffffff;
        }
    </style>
</head>
<body>
<div class="sidebar">
    <h2>Dashboard</h2>
    <a href="<%= request.getContextPath() %>/dashboardHome">Dashboard</a>
    <a href="<%= request.getContextPath() %>/tasks">Tasks</a>
    <a href="<%= request.getContextPath() %>/profile">Profile</a>
    <a href="<%= request.getContextPath() %>/managerChangeRequests">Change Requests</a>
    <a href="<%= request.getContextPath() %>/overTime">Not completed Tasks</a>
</div>

<div class="main-content">
    <c:if test="${not empty sessionScope.successMessage}">
        <div class="alert alert-success">
                ${sessionScope.successMessage}
        </div>
        <c:remove var="successMessage" scope="session"/>
    </c:if>
    <div class="container">
        <div class="card ezy__signin1-form-card">
            <div class="card-body p-md-5">
                <h1 class="ezy__signin1-heading">Change Requests</h1>
                <c:if test="${not empty sessionScope.errorMessage}">
                    <div class="alert alert-danger">
                            ${sessionScope.errorMessage}
                    </div>
                    <c:remove var="errorMessage" scope="session"/>
                </c:if>
                <div class="request-list">
                    <c:forEach var="changeRequest" items="${changeRequests}">
                        <div class="request-item">
                            <div>
                                <span><strong>Requester: </strong>${changeRequest.requester.firstName} ${changeRequest.requester.lastName}</span><br>
                                <span><strong>Task Title: </strong>${changeRequest.task.title}</span><br>
                                <span><strong>Task Description: </strong>${changeRequest.task.description}</span><br>
                                <span><strong>Task Deadline: </strong>${changeRequest.task.deadline}</span><br>
                            </div>
                            <form action="${pageContext.request.contextPath}/resignTask" method="post">
                                <input type="hidden" name="changeRequestId" value="${changeRequest.id}">
                                <label for="newAssigneeId">Reassign to:</label>
                                <select id="newAssigneeId" name="newAssigneeId" class="form-control" required>
                                    <c:forEach var="user" items="${users}">
                                        <option value="${user.id}">${user.firstName} ${user.lastName}</option>
                                    </c:forEach>
                                </select>
                                <button type="submit" class="btn btn-primary mt-2">Reassign Task</button>
                            </form>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>