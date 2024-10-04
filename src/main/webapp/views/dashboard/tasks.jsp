<%--
  Created by IntelliJ IDEA.
  User: Youcode
  Date: 10/4/2024
  Time: 12:30 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <title>Task Manager</title>
  <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
  <style>
    body {
      display: flex;
      height: 100vh;
    }
    .sidebar {
      width: 250px;
      background-color: #343a40;
      color: white;
      padding: 15px;
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
    .content {
      flex: 1;
      padding: 20px;
    }
    .container {
      max-width: 600px;
      width: 100%;
      padding: 20px;
      background-color: white;
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
      border-radius: 8px;
    }
    h1 {
      text-align: center;
      color: #333;
    }
    .task-form {
      display: flex;
      flex-direction: column;
      margin-bottom: 20px;
    }
    .task-form input[type="text"] {
      padding: 10px;
      border: 1px solid #ccc;
      border-radius: 4px;
      margin-bottom: 10px;
    }
    .task-form button {
      padding: 10px;
      border: none;
      color: white;
      background-color: #28a745;
      border-radius: 4px;
      cursor: pointer;
      font-weight: bold;
    }
    .task-form button:hover {
      background-color: #218838;
    }
    .task-list {
      margin-top: 20px;
    }
    .task-item {
      display: flex;
      justify-content: space-between;
      padding: 10px;
      border-bottom: 1px solid #ddd;
    }
    .task-actions button {
      margin-left: 5px;
      padding: 5px 10px;
      border: none;
      border-radius: 4px;
      cursor: pointer;
      font-weight: bold;
    }
    .edit-btn {
      background-color: #ffc107;
      color: white;
    }
    .edit-btn:hover {
      background-color: #e0a800;
    }
    .delete-btn {
      background-color: #dc3545;
      color: white;
    }
    .delete-btn:hover {
      background-color: #c82333;
    }
  </style>
</head>
<body>
<div class="sidebar">
  <h2>Dashboard</h2>
  <a href="<%= request.getContextPath() %>/dashboardHome">Dashboard</a>
  <a href="<%= request.getContextPath() %>/tasks">Tasks</a>
  <a href="<%= request.getContextPath() %>/profile">Profile</a>
</div>
<div class="container">
  <h1>Task Manager</h1>
  <form class="task-form" action="addTask" method="post">
    <input type="text" name="title" placeholder="Enter task title" required>
    <textarea name="description" placeholder="Enter task description" rows="4" required></textarea>
    <input type="date" name="deadline" required>
    <select name="assignedTo" required>
      <option value="">Assign to</option>
      <c:forEach var="user" items="${users}">
        <option value="${user.id}">${user.firstName} ${user.lastName}</option>
        </c:forEach>
    </select>
    <button type="submit">Add Task</button>
  </form>
  <div class="task-list">
    <%-- Replace this with dynamic task list --%>
    <div class="task-item">
      <span>Task Name</span>
      <div class="task-actions">
        <button class="edit-btn" onclick="editTask()">Edit</button>
        <button class="delete-btn" onclick="deleteTask()">Delete</button>
      </div>
    </div>
    <%-- Add more tasks here dynamically --%>
  </div>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script>
  function editTask() {
    alert("Edit task feature coming soon!");
  }
  function deleteTask() {
    alert("Delete task feature coming soon!");
  }
</script>
</body>
</html>

