package com.example.emailsender.app.job;

import com.example.emailsender.app.service.EmailSenderService;
import com.example.emailsender.app.service.MessageService;
import com.example.emailsender.app.service.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
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
            log.info("Sending random message.");
        }
        log.info("Current hour is not scheduled to send message. No messages will be sent.");
    }

    @Scheduled(cron = "0 0 5 * * TUE")
    public void sendBackupEmail() {
        emailSenderService.sendBackupEmail();
        log.info("Sending backup email - every tuesdays at 5am.");
    }
}