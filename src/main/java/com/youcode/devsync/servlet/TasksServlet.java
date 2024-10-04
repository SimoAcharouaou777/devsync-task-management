package com.youcode.devsync.servlet;

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

@WebServlet(name = "TasksServlet", value = "/tasks")
public class TasksServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException{
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");

        boolean isManager = currentUser.getUserRole() == UserRole.MANAGER;

        List<User> users = userService.getUsersByRole(UserRole.USER);
        request.setAttribute("users", users);
        request.setAttribute("isManager", isManager);
        request.getRequestDispatcher("/views/dashboard/tasks.jsp").forward(request, response);
    }
}
