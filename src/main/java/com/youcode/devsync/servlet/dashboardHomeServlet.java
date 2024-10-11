package com.youcode.devsync.servlet;

import com.youcode.devsync.service.DashboardService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "dashboardHomeServlet", value = "/dashboardHome")
public class dashboardHomeServlet extends HttpServlet {

    private final DashboardService dashboardService = new DashboardService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("totalUsers", dashboardService.getTotalUsers());
        request.setAttribute("totalManagers", dashboardService.getTotalManagers());
        request.setAttribute("totalTasks", dashboardService.getTotalTasks());
        request.setAttribute("completedTasks", dashboardService.getCompletedTasks());
        request.setAttribute("nonCompletedTasks", dashboardService.getNonCompletedTasks());

        request.getRequestDispatcher("/views/dashboard/dashboardHome.jsp").forward(request, response);
    }
}
