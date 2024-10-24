package com.youcode.devsync.servlet;

import com.youcode.devsync.model.ChangeRequest;
import com.youcode.devsync.model.Tag;
import com.youcode.devsync.model.Task;
import com.youcode.devsync.model.User;
import com.youcode.devsync.model.enums.UserRole;
import com.youcode.devsync.repository.TagRepository;
import com.youcode.devsync.scheduler.ChangeRequestCheckerJob;
import com.youcode.devsync.service.TagService;
import com.youcode.devsync.service.TaskService;
import com.youcode.devsync.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.quartz.*;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.StdSchedulerFactory;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@WebServlet(name = "TasksServlet", urlPatterns = {"/tasks", "/addTask","/deleteTask","/editTask","/updateTaskStatus","/sendChangeRequest","/deleteAssignedTask","/overTime"})
public class TasksServlet extends HttpServlet {

    private UserService userService;
    private TagService tagService;
    private TaskService taskService;
    private Scheduler scheduler;

    @Override
    public void init() throws ServletException {
        TagRepository tagRepository = new TagRepository();
        userService = new UserService();
        tagService = new TagService(tagRepository);
        taskService = new TaskService();

        try {
            scheduler = new StdSchedulerFactory().getScheduler();

            JobDetail job = JobBuilder.newJob(ChangeRequestCheckerJob.class)
                    .withIdentity("changeRequestCheckerJob", "group1")
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("changeRequestCheckerTrigger", "group1")
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInMinutes(1)
                            .repeatForever())
                    .build();

            if(!scheduler.checkExists(job.getKey())){
                scheduler.scheduleJob(job, trigger);
            }

            scheduler.start();
        } catch (SchedulerException e) {
            throw new ServletException("Failed to initialize Quartz scheduler", e);
        }

    }



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");

        boolean isManager = currentUser.getUserRole() == UserRole.MANAGER;

        if ("/overTime".equals(request.getServletPath())) {
            List<Task> overdueTasks;
            if (isManager) {
                overdueTasks = taskService.getOverdueTasksByCreatorId(currentUser.getId());
            } else {
                overdueTasks = taskService.getOverdueTasksByAssignedToId(currentUser.getId());
            }
            request.setAttribute("overdueTasks", overdueTasks);
            request.getRequestDispatcher("/views/dashboard/overTime.jsp").forward(request, response);
            return;
        }

        List<User> users = userService.getUsersByRole(UserRole.USER);
        request.setAttribute("users", users);
        request.setAttribute("isManager", isManager);

        List<Tag> tags = tagService.getAllTags();
        request.setAttribute("tags", tags);

        List<Task> tasks;
        if(isManager){
            tasks = taskService.getTasksByCreatorId(currentUser.getId());
        }else{
            tasks = taskService.getTasksById(currentUser.getId());
        }
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
        } else if ("/updateTaskStatus".equals(action)) {
            updateTaskStatus(request, response);
        } else if ("/sendChangeRequest".equals(action)) {
            sendChangeRequest(request, response);
        } else if ("/deleteAssignedTask".equals(action)) {
            deleteAssignedTask(request, response);
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
        if(currentUser.getUserRole().equals(UserRole.MANAGER)){
            Timestamp managerMinDeadline = new Timestamp(now.getTime() + 3 * 24 * 60 * 60 * 1000);
            if(deadline.before(managerMinDeadline)){
                session.setAttribute("errorMessage", "The deadline must be at least 3 days from now.");
                response.sendRedirect(request.getContextPath() + "/tasks");
                return;
            }
        }else{
            if (deadline.before(minDeadline)){
                session.setAttribute("errorMessage", "The deadline must be at least 1 days from now.");
                response.sendRedirect(request.getContextPath() + "/tasks");
                return;
            }
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
        if (task == null || (!task.getCreatedBy().getId().equals(currentUser.getId()) && !currentUser.getUserRole().equals(UserRole.MANAGER))) {
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

    protected void updateTaskStatus(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");

        long taskId = Long.parseLong(request.getParameter("taskId"));
        String status = request.getParameter("status");

        Task task = taskService.findById(taskId);
        if (task == null || (!task.getCreatedBy().getId().equals(currentUser.getId()) && !task.getAssignedTo().getId().equals(currentUser.getId()))) {
            session.setAttribute("errorMessage", "Task not found or you do not have permission to update this task.");
            response.sendRedirect(request.getContextPath() + "/tasks");
            return;
        }

        task.setStatus(status);
        taskService.updateTask(task);
        response.sendRedirect(request.getContextPath() + "/tasks");
    }

    private void sendChangeRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        long taskId = Long.parseLong(request.getParameter("taskId"));
        Task task = taskService.findById(taskId);

        if (task == null) {
            session.setAttribute("errorMessage", "Task not found.");
            response.sendRedirect(request.getContextPath() + "/tasks");
            return;
        }

        if (userService.hasChangeRequest(task, currentUser)) {
            session.setAttribute("errorMessage", "You have already sent a change request for this task.");
            response.sendRedirect(request.getContextPath() + "/tasks");
            return;
        }

        if (currentUser.getTickets() <= 0) {
            session.setAttribute("errorMessage", "You do not have enough tickets to make your request.");
            response.sendRedirect(request.getContextPath() + "/tasks");
            return;
        }

        currentUser.setTickets(currentUser.getTickets() - 1);
        userService.updateUserTickets(currentUser);

        ChangeRequest changeRequest = new ChangeRequest();
        changeRequest.setTask(task);
        changeRequest.setRequester(currentUser);
        changeRequest.setManager(task.getCreatedBy());
        changeRequest.setRequestDate(new Timestamp(System.currentTimeMillis()));

        System.out.println("changeRequest : "+changeRequest);

        userService.addChangeRequest(changeRequest);

        System.out.println("changeRequest : "+changeRequest);

        response.sendRedirect(request.getContextPath() + "/tasks");
    }

    private void deleteAssignedTask(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        long taskId = Long.parseLong(request.getParameter("taskId"));
        User currentUser = (User) session.getAttribute("currentUser");

        try {
            if (userService.canDeleteAssignedTask(currentUser)) {
                taskService.deleteTask(taskId);
                userService.updateLastAssignedTaskDeletedAt(currentUser);
                session.setAttribute("successMessage", "Assigned task deleted successfully.");
            } else {
                session.setAttribute("errorMessage", "You can only delete one assigned task per month.");
            }
        } catch (Exception e) {
            session.setAttribute("errorMessage", "Failed to delete assigned task: " + e.getMessage());
        }
        response.sendRedirect(request.getContextPath() + "/tasks");
    }
}