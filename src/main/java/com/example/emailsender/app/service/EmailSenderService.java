package com.example.emailsender.app.service;

import com.example.emailsender.app.dtos.CreateAdditionalMessageOutputDto;
import com.example.emailsender.app.dtos.CreateMessageOutputDto;
import com.example.emailsender.app.mapper.Mapper;
import com.example.emailsender.app.utils.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

import static com.example.emailsender.app.constants.EmailConstants.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailSenderService {

    @Autowired
    private final JavaMailSender mailSender;

    @Autowired
    private final MessageService messageService;

    private Mapper mapper = Mappers.getMapper(Mapper.class);

    private SimpleMailMessage createMailMessage(String subject, String body) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(FROM_EMAIL);
        mailMessage.setTo(TO_EMAIL);
        mailMessage.setSubject(subject);
        mailMessage.setText(body);

        log.info(String.format("Following message will be sent:"), body);

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
        String body = JsonUtils.convertMessagesToJson(mapper.toCreateMessageInputDtoList(messages));

        return createMailMessage(subject, body);
    }

    private String buildRandomEmailBody(CreateMessageOutputDto messageDto) {
        StringBuilder emailContent = new StringBuilder();

        // Main message
        emailContent.append(messageDto.getMessage()).append("\n\n");

        // Additional messages
        if (messageDto.getAdditionalMessages() != null && !messageDto.getAdditionalMessages().isEmpty()) {
            for (CreateAdditionalMessageOutputDto additionalMessage : messageDto.getAdditionalMessages()) {
                emailContent.append("- ").append(additionalMessage.getAdditionalMessage()).append("\n");
            }
        }

        return emailContent.toString();
    }



    public void sendRandomMessageEmail() {
        mailSender.send(buildRandomMessageEmail());
    }

    public void sendBackupEmail() {
        mailSender.send(buildBackupEmail());
    }

}
