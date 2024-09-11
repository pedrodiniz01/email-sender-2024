package com.example.emailsender.app.service;

import com.example.emailsender.app.dtos.CreateMessageOutputDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.example.emailsender.app.constants.EmailConstants.*;

@Service
@RequiredArgsConstructor
public class EmailSenderService {

    @Autowired
    private final JavaMailSender mailSender;

    @Autowired
    private final MessageService messageService;


    private SimpleMailMessage buildEmail() {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        CreateMessageOutputDto message = messageService.getRandomMessage();

        mailMessage.setFrom(FROM_EMAIL);
        mailMessage.setTo(TO_EMAIL);
        mailMessage.setSubject(LocalDate.now() + " - " + EMAIL_SUBJECT);
        mailMessage.setText(buildEmailBody(message));

        return mailMessage;
    }

    private String buildEmailBody(CreateMessageOutputDto messageOutputDto) {
        StringBuilder sb = new StringBuilder();
        return String.valueOf(sb.append("ID: ").append(messageOutputDto.getId())
                .append(", Message: ").append(messageOutputDto.getMessage())
                .append(", Date: ").append(messageOutputDto.getDate()));
    }

    public void sendEmail() {
        mailSender.send(buildEmail());
    }
}
