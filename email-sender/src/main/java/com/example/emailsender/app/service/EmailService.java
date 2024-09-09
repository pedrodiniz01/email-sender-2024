package com.example.emailsender.app.service;

import ch.qos.logback.core.util.StringUtil;
import com.example.emailsender.app.dtos.CreateMessageInputDto;
import com.example.emailsender.app.mapper.MessageMapper;
import com.example.emailsender.app.repository.MessageJpaRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EmailService {

    @Autowired
    private MessageJpaRepository messageRepository;

    private MessageMapper messageMapper = Mappers.getMapper(MessageMapper.class);

    public void saveMessage(CreateMessageInputDto messageInput) {

        String message = messageInput.getMessage();

        if (StringUtil.notNullNorEmpty(message) || messageRepository.existsByMessage(message)) {
            messageRepository.save(messageMapper.toJpa(messageInput));
        }
    }
}
