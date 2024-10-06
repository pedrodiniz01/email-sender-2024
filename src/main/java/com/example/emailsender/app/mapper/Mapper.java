package com.example.emailsender.app.mapper;

import com.example.emailsender.app.dtos.CreateAdditionalMessageOutputDto;
import com.example.emailsender.app.dtos.CreateMessageInputDto;
import com.example.emailsender.app.dtos.CreateMessageOutputDto;
import com.example.emailsender.app.dtos.ScheduleDto;
import com.example.emailsender.app.repository.tables.AdditionalMessageJPA;
import com.example.emailsender.app.repository.tables.MessageJpa;
import com.example.emailsender.app.repository.tables.ScheduleJpa;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@org.mapstruct.Mapper
public interface Mapper {
    Mapper INSTANCE = Mappers.getMapper(Mapper.class);
    MessageJpa toMessageJpa(CreateMessageInputDto dto);
    ScheduleJpa toScheduleJpa(ScheduleDto dto);
    List<CreateMessageOutputDto> toMessageOutputDtoList(List<MessageJpa> jpaList);
    @Mapping(target = "additionalMessages", qualifiedByName = "sortAdditionalMessagesByIdDesc")
    CreateMessageOutputDto toMessageOutputDto(MessageJpa messageJpa);
    List<ScheduleDto> toScheduleDtoList(List<ScheduleJpa> jpaList);
    ScheduleDto toScheduleDto(ScheduleJpa jpa);
    List<CreateMessageInputDto> toCreateMessageInputDtoList (List<CreateMessageOutputDto> createMessageOutputDtos);
    CreateAdditionalMessageOutputDto additionalMessageJPAToCreateAdditionalMessageOutputDto(AdditionalMessageJPA additionalMessageJPA);

    @Named("sortAdditionalMessagesByIdDesc")
    default List<CreateAdditionalMessageOutputDto> sortAdditionalMessagesByIdDesc(List<AdditionalMessageJPA> additionalMessages) {
        if (additionalMessages == null) {
            return null;
        }

        return additionalMessages.stream()
                .sorted((a1, a2) -> Long.compare(a2.getId(), a1.getId()))
                .map(this::additionalMessageJPAToCreateAdditionalMessageOutputDto)
                .toList();
    }
}
