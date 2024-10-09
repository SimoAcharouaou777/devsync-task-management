<%--
  Created by IntelliJ IDEA.
  User: Youcode
  Date: 10/9/2024
  Time: 10:58 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <title>Overdue Tasks</title>
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
  <h1>Overdue Tasks</h1>
  <c:if test="${not empty overdueTasks}">
    <table class="table">
      <thead>
      <tr>
        <th>Title</th>
        <th>Description</th>
        <th>Deadline</th>
        <th>Assigned To</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="task" items="${overdueTasks}">
        <tr>
          <td>${task.title}</td>
          <td>${task.description}</td>
          <td>${task.deadline}</td>
          <td>${task.assignedTo.firstName} ${task.assignedTo.lastName}</td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </c:if>
  <c:if test="${empty overdueTasks}">
    <p>No overdue tasks found.</p>
  </c:if>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
