package com.example.emailsender.app.service;

import com.example.emailsender.app.dtos.CreateMessageOutputDto;
import com.example.emailsender.app.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.example.emailsender.app.constants.EmailConstants.*;

@Service
@RequiredArgsConstructor
public class EmailSenderService {

    @Autowired
    private final JavaMailSender mailSender;

    @Autowired
    private final MessageService messageService;

    private SimpleMailMessage createMailMessage(String subject, String body) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(FROM_EMAIL);
        mailMessage.setTo(TO_EMAIL);
        mailMessage.setSubject(subject);
        mailMessage.setText(body);

        return mailMessage;
    }

    private SimpleMailMessage buildRandomMessageEmail() {
        CreateMessageOutputDto message = messageService.getRandomMessage();

        String subject = LocalDate.now() + " - " + EMAIL_SUBJECT;
        String body = buildRandomEmailBody(message);

        return createMailMessage(subject, body);
    }

    private SimpleMailMessage buildBackupEmail() {
        List<CreateMessageOutputDto> messages = messageService.retrieveAllMessages();

        String subject = LocalDate.now() + " - " + BACK_UP_EMAIL_SUBJECT;
        String body = JsonUtils.convertMessagesToJson(messages);

        return createMailMessage(subject, body);
    }

    private String buildRandomEmailBody(CreateMessageOutputDto messageOutputDto) {
        return BODY_PREFIX + "\n" +
                "\n ID: " + messageOutputDto.getId() +
                "\n Message: " + messageOutputDto.getMessage() +
                "\n Date: " + messageOutputDto.getDate();
    }

    public void sendRandomMessageEmail() {
        mailSender.send(buildRandomMessageEmail());
    }

    public void sendBackupEmail() {
        mailSender.send(buildBackupEmail());
    }
}
