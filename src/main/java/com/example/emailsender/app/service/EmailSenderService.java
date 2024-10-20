package com.example.emailsender.app.service;

import com.example.emailsender.app.dtos.CreateAdditionalMessageOutputDto;
import com.example.emailsender.app.dtos.CreateMessageOutputDto;
import com.example.emailsender.app.mapper.Mapper;
import com.example.emailsender.app.repository.MessageCounterJpaRepository;
import com.example.emailsender.app.repository.tables.AttributesJPA;
import com.example.emailsender.app.repository.tables.CharacterJPA;
import com.example.emailsender.app.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.time.Period;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

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

    @Autowired
    private final MessageCounterJpaRepository messageCounterJpaRepository;

    @Autowired
    private final CharacterService characterService;

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

    public void sendAttributesEmail() {
        CharacterJPA mainCharacter = characterService.getMainCharacter();

        String subject = LocalDate.now() + " - " + CHARACTER_ATTRIBUTES_SUBJECT;

        mailSender.send(createMailMessage(subject, buildAttributesMessage(mainCharacter)));
    }

    private String buildAttributesMessage(CharacterJPA character) {
        AttributesJPA attributes = character.getAttributes();
        int age = calculateAge(character.getBirthDate(), LocalDate.now());

        float sparringHours = attributes.getSparringMinutes() / 60f;
        float sparringDays = attributes.getSparringMinutes() / (60f * 24f);

        float minutesReading = attributes.getPagesRead() > 0 ? attributes.getPagesRead() * 120 : 0;
        float readingHours = minutesReading / 60f;
        float readingDays = minutesReading / (60f * 24f);

        float meditationHours = attributes.getMinutesMeditating() / 60f;
        float meditationDays = attributes.getMinutesMeditating() / (60f * 24f);

        float runningHours = attributes.getMinutesRun() / 60f;
        float runningDays = attributes.getMinutesRun() / (60f * 24f);

        String message = String.format(
                "Hello %s, you are %d years old.\n\n" +
                        "Sparring Minutes: %.1f (%.2f hours, %.2f days)\n\n" +
                        "Pages Read: %d\n\n" +
                        "Averages Minutes Reading: %.1f (%.2f hours, %.2f days)\n\n" +
                        "Meditation Streak: %d days\n\n" +
                        "Minutes Meditating: %d (%.2f hours, %.2f days)\n\n" +
                        "KMs Run: %.2f\n\n" +
                        "Minutes Running: %d (%.2f hours, %.2f days)",
                character.getName(),
                age,
                attributes.getSparringMinutes(), sparringHours, sparringDays,
                attributes.getPagesRead(),
                minutesReading, readingHours, readingDays,
                attributes.getMeditationStreak(),
                attributes.getMinutesMeditating(), meditationHours, meditationDays,
                attributes.getKmsRun(),
                attributes.getMinutesRun(), runningHours, runningDays
        );

        return message;
    }


    private int calculateAge(LocalDate birthdate, LocalDate currentDate) {
        if (birthdate != null) {
            return Period.between(birthdate, currentDate).getYears();
        } else {
            return 0;
        }
    }
}
