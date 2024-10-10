package com.youcode.devsync.scheduler;

import com.youcode.devsync.model.ChangeRequest;
import com.youcode.devsync.model.User;
import com.youcode.devsync.service.UserService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Logger;

public class ChangeRequestCheckerJob implements Job {

    private static final Logger logger = Logger.getLogger(ChangeRequestCheckerJob.class.getName());
    private UserService userService = new UserService();

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("Executing ChangeRequestCheckerJob...");
        List<ChangeRequest> changeRequests = userService.getAllChangeRequests();
        Timestamp now = new Timestamp(System.currentTimeMillis());

        for (ChangeRequest changeRequest : changeRequests) {
            long elapsedTime = now.getTime() - changeRequest.getRequestDate().getTime();

            if (elapsedTime >= 43200000 && !userService.isTaskReassigned(changeRequest.getTask().getId())) {
                User requester = changeRequest.getRequester();
                requester.setTickets(requester.getTickets() + 1);
                userService.updateUserTickets(requester);
                userService.deleteChangeRequest(changeRequest.getId());
                logger.info("Returned ticket to user : "+ requester.getId());
            }

            if (elapsedTime >= 86400000) {
                User requester = changeRequest.getRequester();
                requester.setTickets(requester.getTickets() * 2);
                userService.updateUserTickets(requester);
                userService.deleteChangeRequest(changeRequest.getId());
                logger.info("Doubled tickets for user : "+ requester.getId());
            }
        }
    }
}