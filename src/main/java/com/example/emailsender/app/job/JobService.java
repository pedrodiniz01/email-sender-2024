package com.example.emailsender.app.job;

import com.example.emailsender.app.service.EmailSenderService;
import com.example.emailsender.app.service.MessageService;
import com.example.emailsender.app.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class JobService {

    @Autowired
    ScheduleService scheduleService;
    @Autowired
    MessageService messageService;
    @Autowired
    EmailSenderService emailSenderService;

    @Scheduled(cron = "0 8-23 * * * ?")
    public void trigger() {

        if (scheduleService.isCurrentHourInList()) {
            emailSenderService.sendRandomMessageEmail();
        }
    }
}