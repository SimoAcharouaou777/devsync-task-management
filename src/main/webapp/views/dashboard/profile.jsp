<%@ page import="com.youcode.devsync.model.User" %><%--
  Created by IntelliJ IDEA.
  User: Youcode
  Date: 10/3/2024
  Time: 11:20 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <title>Profile</title>
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
<div class="container">
  <h1>Profile Page</h1>
  <p>Welcome to your profile page.</p>
  <% User user = (User) request.getAttribute("user"); %>
  <form action="<%= request.getContextPath() %>/updateProfile" method="post">
    <div class="form-group">
      <label for="username">Username</label>
      <input type="text" class="form-control" id="username" name="username" value="<%= user != null ? user.getUsername() : "" %>" required>
    </div>
    <div class="form-group">
      <label for="firstName">First Name</label>
      <input type="text" class="form-control" id="firstName" name="firstName" value="<%= user != null ? user.getFirstName() : "" %>" required>
    </div>
    <div class="form-group">
      <label for="lastName">Last Name</label>
      <input type="text" class="form-control" id="lastName" name="lastName" value="<%= user != null ? user.getLastName() : "" %>" required>
    </div>
    <div class="form-group">
      <label for="email">Email</label>
      <input type="email" class="form-control" id="email" name="email" value="<%= user != null ? user.getEmail() : "" %>" required>
    </div>
    <div class="form-group">
      <label for="currentPassword">Current Password</label>
      <input type="password" class="form-control" id="currentPassword" name="currentPassword" required>
    </div>
    <button type="submit" class="btn btn-primary">Update Profile</button>
  </form>
  <form id="deleteAccountForm" action="<%= request.getContextPath() %>/deleteAccount" method="post" style="margin-top: 20px;">
    <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#deleteAccountModal" >Delete Account</button>
  </form>

    <!-- Modal -->
  <div class="modal fade" id="deleteAccountModal" tabindex="-1" role="dialog" aria-labelledby="deleteAccountModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="deleteAccountModalLabel">Confirm Delete Account</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body">
          <form id="confirmDeleteAccountForm" action="<%= request.getContextPath() %>/deleteAccount" method="post">
            <div class="form-group">
              <label for="deleteAccountPassword">Current Password</label>
              <input type="password" class="form-control" id="deleteAccountPassword" name="currentPassword" required>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
              <button type="submit" class="btn btn-danger">Delete Account</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script>
  function validateForm(){
    var currentPassword = document.getElementById("currentPassword").value;
    if(currentPassword === ""){
      alert("Please enter your current password.");
      return false;
    }
    return true;
  }
</script>
</body>
</html>
