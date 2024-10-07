package com.youcode.devsync.servlet;

import com.youcode.devsync.model.Tag;
import com.youcode.devsync.model.Task;
import com.youcode.devsync.model.User;
import com.youcode.devsync.model.enums.UserRole;
import com.youcode.devsync.service.TagService;
import com.youcode.devsync.service.TaskService;
import com.youcode.devsync.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@WebServlet(name = "TasksServlet", urlPatterns = {"/tasks", "/addTask","/deleteTask","/editTask"})
public class TasksServlet extends HttpServlet {

    private UserService userService;
    private TagService tagService;
    private TaskService taskService;

    @Override
    public void init() throws ServletException {
        userService = new UserService();
        tagService = new TagService();
        taskService = new TaskService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");

        boolean isManager = currentUser.getUserRole() == UserRole.MANAGER;

        List<User> users = userService.getUsersByRole(UserRole.USER);
        request.setAttribute("users", users);
        request.setAttribute("isManager", isManager);

        List<Tag> tags = tagService.getAllTags();
        request.setAttribute("tags", tags);

        List<Task> tasks = taskService.getTasksById(currentUser.getId());
        request.setAttribute("tasks", tasks);

        request.getRequestDispatcher("/views/dashboard/tasks.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        if ("/addTask".equals(action)) {
            addTask(request, response);
        } else if ("/deleteTask".equals(action)) {
            deleteTask(request, response);
        } else if ("/editTask".equals(action)) {
            editTask(request, response);
        }
    }

    private void addTask(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        Timestamp deadline = Timestamp.valueOf(request.getParameter("deadline") + " 00:00:00");

        Timestamp now = new Timestamp(System.currentTimeMillis());
        Timestamp minDeadline = new Timestamp(now.getTime() + 24 * 60 * 60 * 1000);

        if (deadline.before(minDeadline)){
            session.setAttribute("errorMessage", "The deadline must be at least 4 days from now.");
            response.sendRedirect(request.getContextPath() + "/tasks");
            return;
        }

        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setDeadline(deadline);
        task.setCreatedBy(currentUser);
        task.setStatus("PENDING");

        if (currentUser.getUserRole().equals(UserRole.MANAGER)) {
            long assignedToId = Long.parseLong(request.getParameter("assignedTo"));
            User assignedTo = userService.findById(assignedToId);
            task.setAssignedTo(assignedTo);
        } else {
            task.setAssignedTo(currentUser);
        }

        String[] tagIds = request.getParameterValues("tags");
        if (tagIds != null) {
            Set<Tag> tags = new HashSet<>();
            for (String tagId : tagIds) {
                Tag tag = tagService.findById(Long.parseLong(tagId));
                tags.add(tag);
            }
            task.setTags(tags);
        }

        taskService.addTask(task);
        response.sendRedirect(request.getContextPath() + "/tasks");
    }

    private void deleteTask(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long taskId = Long.parseLong(request.getParameter("taskId"));
        taskService.deleteTask(taskId);
        response.sendRedirect(request.getContextPath() + "/tasks");
    }

    private void editTask(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");

        long taskId = Long.parseLong(request.getParameter("taskId"));
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        Timestamp deadline = Timestamp.valueOf(request.getParameter("deadline") + " 00:00:00");

        Task task = taskService.findById(taskId);
        if (task == null || task.getAssignedTo() == null || !task.getAssignedTo().getId().equals(currentUser.getId())) {
            session.setAttribute("errorMessage", "Task not found or you do not have permission to edit this task.");
            response.sendRedirect(request.getContextPath() + "/tasks");
            return;
        }

        task.setTitle(title);
        task.setDescription(description);
        task.setDeadline(deadline);
        task.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        String[] tagIds = request.getParameterValues("tags");
        if (tagIds != null) {
            Set<Tag> tags = new HashSet<>();
            for (String tagId : tagIds) {
                Tag tag = tagService.findById(Long.parseLong(tagId));
                tags.add(tag);
            }
            task.setTags(tags);
        }

        taskService.updateTask(task);
        response.sendRedirect(request.getContextPath() + "/tasks");
    }
}