package com.example.emailsender.app.mapper;

import com.example.emailsender.app.dtos.CreateMessageInputDto;
import com.example.emailsender.app.dtos.CreateMessageOutputDto;
import com.example.emailsender.app.repository.tables.MessageJpa;
import org.mapstruct.factory.Mappers;

@org.mapstruct.Mapper
public interface Mapper {
    Mapper INSTANCE = Mappers.getMapper(Mapper.class);
    MessageJpa toJpa(CreateMessageInputDto dto);
    CreateMessageOutputDto toDomain(MessageJpa jpa);
}
