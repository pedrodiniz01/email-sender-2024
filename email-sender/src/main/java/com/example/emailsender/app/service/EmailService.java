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

@Service
public class EmailService {

    @Autowired
    private MessageJpaRepository messageRepository;

    private Mapper mapper = Mappers.getMapper(Mapper.class);

    public CreateMessageOutputDto saveMessage(CreateMessageInputDto messageInput) {

        String message = messageInput.getMessage();

        if (StringUtil.notNullNorEmpty(message) && !messageRepository.existsByMessage(message)) {
            MessageJpa messageJpa = messageRepository.save(mapper.toJpa(messageInput));
            return mapper.toDomain(messageJpa);
        }
        throw new InvalidMessageException("Invalid message.");
    }
}
