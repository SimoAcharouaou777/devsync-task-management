package com.youcode.devsync.scheduler;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.JobDetail;

public class TaskScheduler {

    public static void main(String[] args) {
        try {
            JobDetail job = JobBuilder.newJob(OverdueTaskCheckerJob.class)
                    .withIdentity("overdueTaskCheckerJob", "group1")
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("overdueTaskCheckerTrigger", "group1")
                    .startNow()
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 * * * ?"))
                    .build();

            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }
}