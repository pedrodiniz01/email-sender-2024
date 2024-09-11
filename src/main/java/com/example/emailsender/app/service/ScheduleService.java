package com.example.emailsender.app.service;

import ch.qos.logback.core.util.StringUtil;
import com.example.emailsender.app.dtos.CreateMessageInputDto;
import com.example.emailsender.app.dtos.CreateMessageOutputDto;
import com.example.emailsender.app.dtos.ErrorResponseDto;
import com.example.emailsender.app.dtos.ScheduleDto;
import com.example.emailsender.app.exceptions.InvalidInputException;
import com.example.emailsender.app.mapper.Mapper;
import com.example.emailsender.app.repository.ScheduleJpaRepository;
import com.example.emailsender.app.repository.tables.MessageJpa;
import com.example.emailsender.app.repository.tables.ScheduleJpa;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    private Mapper mapper = Mappers.getMapper(Mapper.class);

    @Autowired
    private ScheduleJpaRepository scheduleJpaRepository;

    public List<ScheduleDto> retrieveAllSchedules() {
        return mapper.toScheduleDtoList(scheduleJpaRepository.findAll());
    }
    public List<ScheduleDto> createSchedule(List<ScheduleDto> scheduleDtoList) {
        List<ScheduleJpa> scheduleJpaList = new ArrayList<>();

        for (ScheduleDto scheduleDto : scheduleDtoList) {

            Integer hour = scheduleDto.getHour();

            ScheduleJpa scheduleJpa = mapper.toScheduleJpa(scheduleDto);

            if (validateHour(hour)) {
                scheduleJpaList.add(scheduleJpa);
                scheduleJpaRepository.save(scheduleJpa);
            } else {
                throw new InvalidInputException("Invalid message.", scheduleJpaList.stream().map(ScheduleJpa::getHour).toList());
            }
        }

        return mapper.toScheduleDtoList(scheduleJpaList);
    }

    private boolean validateHour(Integer hour) {
        return (hour >=0 && hour <= 24) && !scheduleJpaRepository.existsByHour(hour);
    }

    public void deleteAllData() {
        scheduleJpaRepository.deleteAll();
    }

    public ScheduleDto updateHourById(Long id, Integer hour) {
        Optional<ScheduleJpa> scheduleJpaOpt = scheduleJpaRepository.findById(id);

        if (validateHour(hour)) {
            ScheduleJpa scheduleJpa = scheduleJpaOpt.get();

            scheduleJpa.setHour(hour);
            scheduleJpaRepository.save(scheduleJpa);
            return mapper.toScheduleDto(scheduleJpa);
        }
        throw new InvalidInputException(String.format("Error updating hour with id: %d", id), null);
    }

    public ScheduleDto deleteMessageById(Long id) {
        Optional<ScheduleJpa> scheduleJpaOpt = scheduleJpaRepository.findById(id);

        if (scheduleJpaOpt.isPresent()) {
            scheduleJpaRepository.deleteById(id);
            return mapper.toScheduleDto(scheduleJpaOpt.get());
        }
        throw new InvalidInputException(String.format("Id not found: %d", id), null);
    }

    public boolean isCurrentHourInList() {
        int currentHour = LocalTime.now().getHour();

        return scheduleJpaRepository.findAll().stream().map(ScheduleJpa::getHour).toList().contains(currentHour);
    }
}
