package com.example.emailsender.app.mapper;

import com.example.emailsender.app.dtos.CreateMessageInputDto;
import com.example.emailsender.app.dtos.CreateMessageOutputDto;
import com.example.emailsender.app.repository.tables.MessageJpa;
import org.mapstruct.factory.Mappers;

import java.util.List;

@org.mapstruct.Mapper
public interface Mapper {
    Mapper INSTANCE = Mappers.getMapper(Mapper.class);
    MessageJpa toJpa(CreateMessageInputDto dto);
    List<CreateMessageOutputDto> toDomain(List<MessageJpa> jpaList);
    CreateMessageOutputDto toDomain(MessageJpa jpa);
}
