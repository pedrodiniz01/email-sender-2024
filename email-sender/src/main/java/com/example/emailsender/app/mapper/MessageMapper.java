package com.example.emailsender.app.mapper;

import com.example.emailsender.app.dtos.CreateMessageInputDto;
import com.example.emailsender.app.repository.tables.MessageJpa;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MessageMapper {
    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);
    MessageJpa toJpa(CreateMessageInputDto dto);
    CreateMessageInputDto toDto(MessageJpa entity);
}
