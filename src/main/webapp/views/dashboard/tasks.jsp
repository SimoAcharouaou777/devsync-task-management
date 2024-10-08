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
      margin-bottom: 30px;
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
      align-items: center;
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
  <a href="<%= request.getContextPath() %>/managerChangeRequests">Change Requests</a>
</div>

<div class="main-content">
  <div class="container">
    <h1>Task Manager</h1>

    <c:if test="${not empty sessionScope.errorMessage}">
      <div class="alert alert-danger">
          ${sessionScope.errorMessage}
      </div>
      <c:remove var="errorMessage" scope="session"/>
    </c:if>

    <form class="task-form" action="${pageContext.request.contextPath}/addTask" method="post">
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
        <c:forEach var="tag" items="${tags}">
          <option value="${tag.id}">${tag.name}</option>
        </c:forEach>
      </select>

      <button type="submit">Add Task</button>
    </form>

    <div class="task-list">
  <c:forEach var="task" items="${tasks}">
    <c:if test="${currentUser.userRole == 'MANAGER' && task.createdBy.id == currentUser.id || currentUser.userRole == 'USER' && (task.createdBy.id == currentUser.id || task.assignedTo.id == currentUser.id)}">
      <div class="task-item">
        <div>
          <span><strong>Title : </strong>${task.title}</span><br>
          <span><strong>Description : </strong>${task.description}</span><br>
          <span><strong>Deadline : </strong>${task.deadline}</span><br>
          <c:if test="${task.createdBy.id != task.assignedTo.id}">
            <span><strong>Assigned By : </strong>${task.createdBy.firstName} ${task.createdBy.lastName}</span><br>
          </c:if>
          <span><strong>Status : </strong>${task.status}</span><br>
          <c:if test="${task.assignedTo.id == currentUser.id}">
            <form action="${pageContext.request.contextPath}/updateTaskStatus" method="post" style="display: inline;">
              <input type="hidden" name="taskId" value="${task.id}">
              <select name="status" class="form-control">
                <option value="PENDING" ${task.status == 'PENDING' ? 'selected' : ''}>Pending</option>
                <option value="TO_DO" ${task.status == 'TO_DO' ? 'selected' : ''}>To Do</option>
                <option value="DOING" ${task.status == 'DOING' ? 'selected' : ''}>Doing</option>
                <option value="DONE" ${task.status == 'DONE' ? 'selected' : ''}>Done</option>
              </select>
              <button type="submit" class="btn btn-primary mt-2">Update Status</button>
            </form>
          </c:if>
        </div>
        <div class="task-actions">
          <c:if test="${task.createdBy.id == currentUser.id || (currentUser.userRole == 'MANAGER' && task.assignedTo.id == currentUser.id)}">
            <button type="button" class="edit-btn" onclick="openEditModal(${task.id}, '${task.title}', '${task.description}', '${task.deadline}', [<c:forEach var='tag' items='${task.tags}'>${tag.id},</c:forEach>])">Edit</button>
          </c:if>
          <c:if test="${task.createdBy.id == currentUser.id || task.assignedTo.id == currentUser.id}">
            <form action="${pageContext.request.contextPath}/deleteTask" method="post" style="display: inline;">
              <input type="hidden" name="taskId" value="${task.id}">
              <button class="delete-btn" type="submit">Delete</button>
            </form>
          </c:if>
          <c:if test="${task.assignedTo.id == currentUser.id && task.createdBy.id != currentUser.id}">
              <form action="${pageContext.request.contextPath}/sendChangeRequest" method="post" style="display: inline;">
                <input type="hidden" name="taskId" value="${task.id}">
                <button class="btn btn-warning" type="submit">Send Change Request</button>
              </form>
          </c:if>
        </div>
      </div>
    </c:if>
  </c:forEach>
</div>
  </div>
</div>

<!-- Edit Task Modal -->

<div class="modal fade" id="editTaskModal" tabindex="-1" role="dialog" aria-labelledby="editTaskModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <form id="editTaskForm" action="${pageContext.request.contextPath}/editTask" method="post">
        <div class="modal-header">
          <h5 class="modal-title" id="editTaskModalLabel">Edit Task</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body">
          <input type="hidden" name="taskId" id="editTaskId">
          <div class="form-group">
            <label for="editTaskTitle">Title</label>
            <input type="text" class="form-control" id="editTaskTitle" name="title" required>
          </div>
          <div class="form-group">
            <label for="editTaskDescription">Description</label>
            <textarea class="form-control" id="editTaskDescription" name="description" rows="4" required></textarea>
          </div>
          <div class="form-group">
            <label for="editTaskDeadline">Deadline</label>
            <input type="date" class="form-control" id="editTaskDeadline" name="deadline" required>
          </div>
          <div class="form-group">
            <label for="editTaskTags">Tags</label>
            <select class="form-control" id="editTaskTags" name="tags" multiple>
              <c:forEach var="tag" items="${tags}">
                <option value="${tag.id}">${tag.name}</option>
              </c:forEach>
            </select>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
          <button type="submit" class="btn btn-primary">Save changes</button>
        </div>
      </form>
    </div>
  </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script>
  function openEditModal(taskId, title, description, deadline, tags) {
    document.getElementById('editTaskId').value = taskId;
    document.getElementById('editTaskTitle').value = title;
    document.getElementById('editTaskDescription').value = description;
    document.getElementById('editTaskDeadline').value = deadline;

    const tagsSelect = document.getElementById('editTaskTags');
    for (let i = 0; i < tagsSelect.options.length; i++) {
      tagsSelect.options[i].selected = tags.includes(parseInt(tagsSelect.options[i].value));
    }

    $('#editTaskModal').modal('show');
  }
</script>
</body>
</html>