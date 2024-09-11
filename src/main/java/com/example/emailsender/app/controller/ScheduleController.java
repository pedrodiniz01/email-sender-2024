package com.example.emailsender.app.controller;

import com.example.emailsender.app.dtos.CreateMessageInputDto;
import com.example.emailsender.app.dtos.ErrorResponseDto;
import com.example.emailsender.app.dtos.ScheduleDto;
import com.example.emailsender.app.exceptions.InvalidInputException;
import com.example.emailsender.app.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @PostMapping("/create")
    public ResponseEntity<?> createSchedule(@RequestBody List<ScheduleDto> scheduleDtoList) {
        try {
            return new ResponseEntity<>(scheduleService.createSchedule(scheduleDtoList), HttpStatus.CREATED);
        } catch (InvalidInputException exception) {
            return new ResponseEntity<>(new ErrorResponseDto(exception.getMessage(),
                    exception.getAdditionalInformation().toString()), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/all")
    public ResponseEntity<?> retrieveAllMessages() {
        return new ResponseEntity<>(scheduleService.retrieveAllSchedules(), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAll() {
        scheduleService.deleteAllData();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateHourById(@PathVariable Long id, @RequestBody ScheduleDto scheduleDto) {
        try {
            return new ResponseEntity<>(scheduleService.updateHourById(id, scheduleDto.getHour()), HttpStatus.OK);
        } catch (InvalidInputException exception) {
            return new ResponseEntity<>(new ErrorResponseDto(exception.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(scheduleService.deleteMessageById(id), HttpStatus.OK);
        } catch (InvalidInputException exception) {
            return new ResponseEntity<>(new ErrorResponseDto(exception.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }
}
