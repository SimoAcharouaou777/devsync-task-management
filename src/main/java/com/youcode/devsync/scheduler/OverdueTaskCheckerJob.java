package com.youcode.devsync.scheduler;

import com.youcode.devsync.model.Task;
import com.youcode.devsync.service.TaskService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Logger;

public class OverdueTaskCheckerJob implements Job {

    private static final Logger logger = Logger.getLogger(OverdueTaskCheckerJob.class.getName());
    private TaskService taskService = new TaskService();

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("Executing OverdueTaskCheckerJob...");
        List<Task> allTasks = taskService.getAllTasks();
        Timestamp now = new Timestamp(System.currentTimeMillis());

        for (Task task : allTasks) {
            if (task.getDeadline().before(now) && !task.getStatus().equals("DONE")) {
                task.setOverdue(true);
                taskService.updateTask(task);
                logger.info("Task with id " + task.getId() + " is overdue");
            }
        }
    }
}