package com.youcode.devsync.servlet;

import com.youcode.devsync.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
@WebServlet(name = "SigninServlet", value = "/signin")
public class SigninServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException{
         userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/views/signin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request , HttpServletResponse response) throws ServletException , IOException{
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if(userService.authenticateUser(username, password)){
            response.sendRedirect(request.getContextPath() + "/home");
        }else{
            response.sendRedirect(request.getContextPath() + "/signin?error=true");
        }
    }

    @Override
    public void destroy(){
        userService.close();
    }
}
