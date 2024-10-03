package com.youcode.devsync.servlet;

import com.youcode.devsync.model.User;
import com.youcode.devsync.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

@WebServlet(name = "ProfileServlet", urlPatterns ={"/profile", "/updateProfile", "/deleteAccount"})
public class ProfileServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException{
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request , HttpServletResponse response) throws ServletException, IOException{
        String username = (String) request.getSession().getAttribute("username");
        User user = userService.findByUsername(username);
        request.setAttribute("user", user);
        request.getRequestDispatcher("/views/dashboard/profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request , HttpServletResponse response) throws ServletException, IOException{
          String action = request.getServletPath();

          if("/updateProfile".equals(action)){
              updateProfile(request, response);
          } else if ("/deleteAccount".equals(action)){
              deleteAccount(request, response);
          }
    }

    private void updateProfile(HttpServletRequest request , HttpServletResponse response) throws ServletException,IOException{
        String currentUsername = (String) request.getSession().getAttribute("username");
        String password = (String) request.getSession().getAttribute("password");
        String newUsername = request.getParameter("username");
        String currentPassword = request.getParameter("currentPassword");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");

        boolean isAuthenticated = userService.authenticateUser(currentUsername, currentPassword);
        if(isAuthenticated){
            boolean isUpdated = userService.updateUser(currentUsername, currentPassword, firstName, lastName, email);
            if(isUpdated){
                if(!currentPassword.equals(password)){
                    userService.updateUsername(currentUsername, newUsername);
                    request.getSession().setAttribute("username", newUsername);
                }
                response.sendRedirect(request.getContextPath() + "/profile");
            }else{
                response.sendRedirect(request.getContextPath() + "/profile?error=true");
            }
        }

    }

    private void deleteAccount(HttpServletRequest request , HttpServletResponse response) throws ServletException,IOException{
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String currentUsername = (String) request.getSession().getAttribute("username");
        String hashedPassword = (String) request.getSession().getAttribute("password");
        String password = request.getParameter("currentPassword");

        if(hashedPassword == null){
            response.sendRedirect(request.getContextPath() + "/profile?nopassword");
            return;
        }

        if(BCrypt.checkpw(password, hashedPassword)){
            userService.deleteUser(currentUsername);
            request.getSession().invalidate();
            response.sendRedirect(request.getContextPath() + "/signin?accountDeleted=true");
        }else{
            response.sendRedirect(request.getContextPath() + "/profile?error=true");
        }
    }
}
