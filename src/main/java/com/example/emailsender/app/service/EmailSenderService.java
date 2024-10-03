package com.example.emailsender.app.service;

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

    private String buildRandomEmailBody(CreateMessageOutputDto messageOutputDto) {
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.registerModule(new JavaTimeModule());

        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));

        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(messageOutputDto);
        } catch (JsonProcessingException e) {
            log.error("Error converting message to formatted JSON", e);
            throw new RuntimeException("Error converting message to formatted JSON", e);
        }
    }



    public void sendRandomMessageEmail() {
        mailSender.send(buildRandomMessageEmail());
    }

    public void sendBackupEmail() {
        mailSender.send(buildBackupEmail());
    }

}
