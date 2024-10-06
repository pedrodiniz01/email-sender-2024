package com.example.emailsender.app.service;

import com.example.emailsender.app.dtos.ScheduleDto;
import com.example.emailsender.app.exceptions.InvalidInputException;
import com.example.emailsender.app.mapper.Mapper;
import com.example.emailsender.app.repository.ScheduleJpaRepository;
import com.example.emailsender.app.repository.tables.ScheduleJpa;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ScheduleService {

    private Mapper mapper = Mappers.getMapper(Mapper.class);

    @Autowired
    private ScheduleJpaRepository scheduleJpaRepository;

    public List<ScheduleDto> retrieveAllSchedules() {
        List<ScheduleDto> scheduleDtoList = mapper.toScheduleDtoList(scheduleJpaRepository.findAll());

        return scheduleDtoList.stream()
                .sorted(Comparator.comparing(ScheduleDto::getHour))
                .collect(Collectors.toList());
    }
    public List<ScheduleDto> createSchedule(List<ScheduleDto> scheduleDtoList) {
        List<ScheduleJpa> scheduleJpaList = new ArrayList<>();

        for (ScheduleDto scheduleDto : scheduleDtoList) {

            Integer hour = scheduleDto.getHour();

            ScheduleJpa scheduleJpa = mapper.toScheduleJpa(scheduleDto);

            if (validateHour(hour)) {
                scheduleJpaList.add(scheduleJpa);
                scheduleJpaRepository.save(scheduleJpa);
                log.info(String.format("Saving schedule hour: "), hour);
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
