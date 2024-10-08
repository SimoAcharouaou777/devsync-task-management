package com.youcode.devsync.servlet;

import com.youcode.devsync.model.ChangeRequest;
import com.youcode.devsync.model.Task;
import com.youcode.devsync.model.User;
import com.youcode.devsync.model.enums.UserRole;
import com.youcode.devsync.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ManagerChangeRequestServlet", urlPatterns = {"/managerChangeRequests", "/resignTask"})
public class ManagerChangeRequestServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");

        if (!currentUser.getUserRole().equals(UserRole.MANAGER)) {
            session.setAttribute("errorMessage", "You do not have permission to access this page.");
            response.sendRedirect(request.getContextPath() + "/tasks");
            return;
        }

        List<ChangeRequest> changeRequests = userService.getAllByManager(currentUser.getId());
        List<User> users = userService.getUsersByRole(UserRole.USER);
        request.setAttribute("changeRequests", changeRequests);
        request.setAttribute("users", users);
        request.getRequestDispatcher("/views/dashboard/managerChangeRequest.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");

        if (!currentUser.getUserRole().equals(UserRole.MANAGER)) {
            session.setAttribute("errorMessage", "You do not have permission to perform this action.");
            response.sendRedirect(request.getContextPath() + "/tasks");
            return;
        }

        if ("/resignTask".equals(action)) {
            resignTask(request, response, session);
        } else {
            response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "POST method is not supported for this URL");
        }
    }

    private void resignTask(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        long changeRequestId = Long.parseLong(request.getParameter("changeRequestId"));
        long newAssigneeId = Long.parseLong(request.getParameter("newAssigneeId"));

        try {
            userService.reassignTask(changeRequestId, newAssigneeId);
            Task task = userService.findTaskByChangeRequestId(changeRequestId); // Fetch the task associated with the change request
            userService.updateTaskCanBeReassigned(task.getId(), false); // Update the flag
            userService.deleteChangeRequest(changeRequestId);
            session.setAttribute("successMessage", "Task reassigned successfully.");
        } catch (Exception e) {
            session.setAttribute("errorMessage", "Failed to reassign task: " + e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/managerChangeRequests");
    }
}