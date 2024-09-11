package com.example.emailsender.app.job;

import com.example.emailsender.app.dtos.CreateMessageOutputDto;
import com.example.emailsender.app.service.EmailSenderService;
import com.example.emailsender.app.service.MessageService;
import com.example.emailsender.app.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Job {

    @Autowired
    ScheduleService scheduleService;
    @Autowired
    MessageService messageService;
    @Autowired
    EmailSenderService emailSenderService;

    @Scheduled(cron = "0 8-23 * * * ?")
    public void sendEmailJob() {
        boolean b = scheduleService.isCurrentHourInList();
        CreateMessageOutputDto a = messageService.getRandomMessage();
        emailSenderService.sendEmail();

        System.out.println("Task running every hour between 8 AM and midnight");
    }
}