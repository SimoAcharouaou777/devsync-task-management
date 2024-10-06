<%--
  Created by IntelliJ IDEA.
  User: Youcode
  Date: 10/4/2024
  Time: 12:30 PM
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
    .task-form {
      padding: 20px;
      background-color: white;
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
      border-radius: 8px;
    }
    h1 {
      color: #333;
      margin-bottom: 20px;
    }
    .task-form input, .task-form textarea, .task-form select {
      padding: 10px;
      border: 1px solid #ccc;
      border-radius: 4px;
      margin-bottom: 15px;
      width: 100%;
    }
    .task-form button {
      padding: 10px;
      background-color: #28a745;
      border: none;
      border-radius: 4px;
      color: white;
      font-weight: bold;
      width: 100%;
    }
    .task-form button:hover {
      background-color: #218838;
    }
    .task-list {
      margin-top: 30px;
    }
    .task-item {
      background-color: white;
      box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
      padding: 15px;
      margin-bottom: 15px;
      border-radius: 8px;
      display: flex;
      justify-content: space-between;
    }
    .task-actions button {
      margin-left: 10px;
      padding: 6px 12px;
      border: none;
      border-radius: 4px;
      font-weight: bold;
      cursor: pointer;
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

<div class="main-content">
  <div class="container">
    <h1>Task Manager</h1>
    <form class="task-form" action="addTask" method="post">
      <input type="text" name="title" placeholder="Enter task title" required>
      <textarea name="description" placeholder="Enter task description" rows="4" required></textarea>
      <input type="date" name="deadline" required>

      <c:if test="${isManager}">
        <select name="assignedTo" required>
          <option value="">Assign to</option>
          <c:forEach var="user" items="${users}">
            <option value="${user.id}">${user.firstName} ${user.lastName}</option>
          </c:forEach>
        </select>
      </c:if>

      <label for="tags">Select Tags:</label>
      <select name="tags" id="tags" multiple class="form-control">
        <option value="1">Tag 1</option>
        <option value="2">Tag 2</option>
        <option value="3">Tag 3</option>
        <option value="4">Tag 4</option>
      </select>

      <button type="submit">Add Task</button>
    </form>

    <div class="task-list">
      <%-- Dynamic task list --%>

        <div class="task-item">
          <span>Task 1</span>
          <div class="task-actions">
            <button class="edit-btn" onclick="editTask(1)">Edit</button>
            <button class="delete-btn" onclick="deleteTask(1)">Delete</button>
          </div>
        </div>

    </div>
  </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script>
  function editTask(taskId) {
    alert("Edit task with ID " + taskId + " coming soon!");
  }
  function deleteTask(taskId) {
    alert("Delete task with ID " + taskId + " coming soon!");
  }
</script>
</body>
</html>
