package com.example.emailsender.app.service;

import ch.qos.logback.core.util.StringUtil;
import com.example.emailsender.app.dtos.CreateMessageInputDto;
import com.example.emailsender.app.dtos.CreateMessageOutputDto;
import com.example.emailsender.app.exceptions.InvalidMessageException;
import com.example.emailsender.app.mapper.Mapper;
import com.example.emailsender.app.repository.MessageJpaRepository;
import com.example.emailsender.app.repository.tables.MessageJpa;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmailService {

    @Autowired
    private MessageJpaRepository messageRepository;

    private Mapper mapper = Mappers.getMapper(Mapper.class);

    public List<CreateMessageOutputDto> saveMessages(List<CreateMessageInputDto> messageList) {

        List<MessageJpa> messageJpaList = new ArrayList<>();

        for (CreateMessageInputDto messageInput : messageList) {

            String message = messageInput.getMessage();

            MessageJpa messageJpa = mapper.toJpa(messageInput);
            messageJpaList.add(messageJpa);

            if (StringUtil.notNullNorEmpty(message) && !messageRepository.existsByMessage(message)) {
                messageRepository.save(messageJpa);
            } else {
                throw new InvalidMessageException("Invalid message.", messageJpaList.stream().map(MessageJpa::getMessage).toList());
            }
        }

        return mapper.toDomain(messageJpaList);
    }

    public void deleteAllData() {
        messageRepository.deleteAll();
    }
}
