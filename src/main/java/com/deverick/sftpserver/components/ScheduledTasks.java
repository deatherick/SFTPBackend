package com.deverick.sftpserver.components;

import com.deverick.sftpserver.services.ScheduledService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    @Autowired
    private ScheduledService demoService;

    //@Scheduled(cron = "0 * * * * *") // Cron expression for running every minute for testing
    @Scheduled(cron = "0 0 12 * * ?") // Cron expression for running every day
    public void execute() {
        demoService.dummyMethod();
    }
}
