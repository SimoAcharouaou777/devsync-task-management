package com.youcode.devsync.servlet;

import com.youcode.devsync.model.User;
import com.youcode.devsync.model.enums.UserRole;
import com.youcode.devsync.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
        List<User> users = userService.getUsersByRole(UserRole.USER);
        request.setAttribute("users", users);
        request.getRequestDispatcher("/views/dashboard/tasks.jsp").forward(request, response);
    }
}
