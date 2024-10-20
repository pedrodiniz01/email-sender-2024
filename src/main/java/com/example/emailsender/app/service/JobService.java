package com.example.emailsender.app.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class JobService {

    @Autowired
    ScheduleService scheduleService;
    @Autowired
    MessageService messageService;
    @Autowired
    EmailSenderService emailSenderService;

    @Transactional
    @Scheduled(cron = "0 0 8-23 * * ?")
    public void trigger() {

        if (scheduleService.isCurrentHourInList()) {
            emailSenderService.sendRandomMessageEmail();
            log.info("Sending random message.");
        }
        else {
            log.info("Current hour is not scheduled to send message. No messages will be sent.");
        }
    }
    @Transactional
    @Scheduled(cron = "0 0 5 ? * TUE")
    public void sendBackupEmail() {
        emailSenderService.sendBackupEmail();
        log.info("Sending backup email - every tuesdays at 5am.");
    }

    @Transactional
    @Scheduled(cron = "0 0 5 ? * TUE")
    public void sendAttributesEmail() {
        emailSenderService.sendAttributesEmail();
        log.info("Sending backup email - every tuesdays at 5am.");
    }
}