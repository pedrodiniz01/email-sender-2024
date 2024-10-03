package com.example.emailsender.app.service;

import ch.qos.logback.core.util.StringUtil;
import com.example.emailsender.app.dtos.AdditionalMessageInputDto;
import com.example.emailsender.app.dtos.CreateMessageInputDto;
import com.example.emailsender.app.dtos.CreateMessageOutputDto;
import com.example.emailsender.app.exceptions.InvalidInputException;
import com.example.emailsender.app.mapper.Mapper;
import com.example.emailsender.app.repository.AdditionalMessageJpaRepository;
import com.example.emailsender.app.repository.MessageJpaRepository;
import com.example.emailsender.app.repository.tables.AdditionalMessageJPA;
import com.example.emailsender.app.repository.tables.MessageJpa;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
public class MessageService {

    @Autowired
    private MessageJpaRepository messageRepository;
    @Autowired
    private AdditionalMessageJpaRepository additionalMessageJpaRepository;

    private Mapper mapper = Mappers.getMapper(Mapper.class);

    public List<CreateMessageOutputDto> createMessages(List<CreateMessageInputDto> messageList) {

        List<MessageJpa> messageJpaList = new ArrayList<>();

        for (CreateMessageInputDto messageInput : messageList) {

            String message = messageInput.getMessage();

            MessageJpa messageJpa = mapper.toMessageJpa(messageInput);
            messageJpaList.add(messageJpa);

            if (StringUtil.notNullNorEmpty(message) && !messageRepository.existsByMessage(message)) {
                messageRepository.save(messageJpa);
                log.info(String.format("Message succesfully saved: ", message));
            } else {
                log.info(String.format("Error saving message: ", message));
                throw new InvalidInputException("Invalid message.", messageJpaList.stream().map(MessageJpa::getMessage).toList());
            }
        }

        return mapper.toMessageOutputDtoList(messageJpaList);
    }
    public CreateMessageOutputDto createAdditionalMessage(Long id, AdditionalMessageInputDto message) {

        Optional<MessageJpa> messageJpaOpt = messageRepository.findById(id);

        if (messageJpaOpt.isPresent()) {
            List<AdditionalMessageJPA> additionalMessageJPAList = messageJpaOpt.get().getAdditionalMessages();

            AdditionalMessageJPA additionalMessageJPA = new AdditionalMessageJPA();
            additionalMessageJPA.setAdditionalMessage(message.getMessage());
            additionalMessageJPA.setMessageJpa(messageJpaOpt.get());

            additionalMessageJPAList.add(additionalMessageJPA);

            messageRepository.save(messageJpaOpt.get());

            log.info(String.format("Message succesfully uppdated: ", messageJpaOpt.get().getMessage()));
            return mapper.toMessageOutputDto(messageRepository.findById(additionalMessageJPA.getMessageId()).get());
        }
        throw new InvalidInputException(String.format("Id not found: %d", id), null);
    }

    public CreateMessageOutputDto deleteMessageById(Long id) {
        Optional<MessageJpa> messageJpaOpt = messageRepository.findById(id);

        if (messageJpaOpt.isPresent()) {
            messageRepository.deleteById(id);
            log.info(String.format("Message succesfully deleted: ", messageJpaOpt.get().getMessage()));
            return mapper.toMessageOutputDto(messageJpaOpt.get());
        }
        throw new InvalidInputException(String.format("Id not found: %d", id), null);
    }

    public CreateMessageOutputDto updateMainMessageById(Long id, String message) {
        Optional<MessageJpa> messageJpaOpt = messageRepository.findById(id);

        if (StringUtil.notNullNorEmpty(message) && messageRepository.existsById(id)) {
            MessageJpa messageJpa = messageJpaOpt.get();

            messageJpa.setMessage(message);
            messageRepository.save(messageJpa);
            log.info(String.format("Message succesfully updated: ", message));
            return mapper.toMessageOutputDto(messageJpa);
        }
        throw new InvalidInputException(String.format("Error updating main message with id: %d", id), null);
    }

    public CreateMessageOutputDto updateAdditionalMessageById(Long id, String message) {
        Optional<AdditionalMessageJPA> optAdditionalMessageJPA = additionalMessageJpaRepository.findById(id);

        if (StringUtil.notNullNorEmpty(message) && additionalMessageJpaRepository.existsById(id)) {
            AdditionalMessageJPA additionalMessageJPA = optAdditionalMessageJPA.get();

            additionalMessageJPA.setAdditionalMessage(message);
            additionalMessageJpaRepository.save(additionalMessageJPA);
            log.info(String.format("Message succesfully updated: ", message));
            return mapper.toMessageOutputDto(messageRepository.findById(additionalMessageJPA.getMessageId()).get());
        }
        throw new InvalidInputException(String.format("Error updating additional message with id: %d", id), null);
    }

    public CreateMessageOutputDto deleteAdditionalMessageById(Long id) {
        Optional<AdditionalMessageJPA> optAdditionalMessageJPA = additionalMessageJpaRepository.findById(id);

        if (optAdditionalMessageJPA.isPresent()) {
            AdditionalMessageJPA additionalMessageJPA = optAdditionalMessageJPA.get();
            additionalMessageJpaRepository.deleteById(id);
            log.info(String.format("Message succesfully deleted: ", optAdditionalMessageJPA.get().getAdditionalMessage()));
            return mapper.toMessageOutputDto(messageRepository.findById(additionalMessageJPA.getMessageId()).get());
        }

        throw new InvalidInputException(String.format("Error updating additional message with id: %d", id), null);
    }

    public void deleteAllData() {
        messageRepository.deleteAll();
    }

    public List<CreateMessageOutputDto> retrieveAllMessages() {
        log.info("Retrieving all messages.");
        return mapper.toMessageOutputDtoList(messageRepository.findAll());
       }

    public CreateMessageOutputDto getRandomMessage() {

        List<Long> ids = messageRepository.findAll().stream().map(MessageJpa::getId).toList();

        if(!ids.isEmpty()) {
            Random random = new Random();
            int randomId = random.nextInt(ids.size());

            Integer id = Math.toIntExact(ids.get(randomId));

            return mapper.toMessageOutputDto(messageRepository.findById((long) id).get());
        }
        throw new RuntimeException("Error getting random message.");
    }
}
