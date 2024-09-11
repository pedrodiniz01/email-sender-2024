package com.example.emailsender.app.mapper;

import com.example.emailsender.app.dtos.CreateMessageInputDto;
import com.example.emailsender.app.dtos.CreateMessageOutputDto;
import com.example.emailsender.app.dtos.ScheduleDto;
import com.example.emailsender.app.repository.tables.MessageJpa;
import com.example.emailsender.app.repository.tables.ScheduleJpa;
import org.mapstruct.factory.Mappers;

import java.util.List;

@org.mapstruct.Mapper
public interface Mapper {
    Mapper INSTANCE = Mappers.getMapper(Mapper.class);
    MessageJpa toMessageJpa(CreateMessageInputDto dto);
    ScheduleJpa toScheduleJpa(ScheduleDto dto);
    List<CreateMessageOutputDto> toMessageOutputDtoList(List<MessageJpa> jpaList);
    CreateMessageOutputDto toMessageOutputDto(MessageJpa messageJpa);
    List<ScheduleDto> toScheduleDtoList(List<ScheduleJpa> jpaList);
    ScheduleDto toScheduleDto(ScheduleJpa jpa);
}
