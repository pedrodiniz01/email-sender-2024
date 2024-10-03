package com.example.emailsender.app.controller;

import com.example.emailsender.app.dtos.AdditionalMessageInputDto;
import com.example.emailsender.app.dtos.CreateMessageInputDto;
import com.example.emailsender.app.dtos.ErrorResponseDto;
import com.example.emailsender.app.exceptions.InvalidInputException;
import com.example.emailsender.app.job.JobService;
import com.example.emailsender.app.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private JobService jobService;

    @PostMapping("/create")
    public ResponseEntity<?> createMessage(@RequestBody List<CreateMessageInputDto> message) {
        try {
            return new ResponseEntity<>(messageService.createMessages(message), HttpStatus.CREATED);
        } catch (InvalidInputException exception) {
            return new ResponseEntity<>(new ErrorResponseDto(exception.getMessage(),
                    exception.getAdditionalInformation().toString()), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/additional/{id}/create")
    public ResponseEntity<?> createAdditionalMessage(@PathVariable Long id, @RequestBody AdditionalMessageInputDto message) {
        try {
            return new ResponseEntity<>(messageService.createAdditionalMessage(id, message), HttpStatus.CREATED);
        } catch (InvalidInputException exception) {
            return new ResponseEntity<>(new ErrorResponseDto(exception.getMessage(),
                    exception.getAdditionalInformation().toString()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(messageService.deleteMessageById(id), HttpStatus.OK);
        } catch (InvalidInputException exception) {
            return new ResponseEntity<>(new ErrorResponseDto(exception.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }
 @DeleteMapping("/additional/{id}")
    public ResponseEntity<?> deleteAdditionalMessageById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(messageService.deleteAdditionalMessageById(id), HttpStatus.OK);
        } catch (InvalidInputException exception) {
            return new ResponseEntity<>(new ErrorResponseDto(exception.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAll() {
        messageService.deleteAllData();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateMainMessageById(@PathVariable Long id, @RequestBody CreateMessageInputDto updateMessageDto) {
        try {
            return new ResponseEntity<>(messageService.updateMainMessageById(id, updateMessageDto.getMainMessage()), HttpStatus.OK);
        } catch (InvalidInputException exception) {
            return new ResponseEntity<>(new ErrorResponseDto(exception.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }@PatchMapping("/additional/{id}")
    public ResponseEntity<?> updateAdditionalMessageById(@PathVariable Long id, @RequestBody AdditionalMessageInputDto additionalMessageInputDto) {
        try {
            return new ResponseEntity<>(messageService.updateAdditionalMessageById(id, additionalMessageInputDto.getMessage()), HttpStatus.OK);
        } catch (InvalidInputException exception) {
            return new ResponseEntity<>(new ErrorResponseDto(exception.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> retrieveAllMessages() {
        return new ResponseEntity<>(messageService.retrieveAllMessages(), HttpStatus.OK);
    }

    @GetMapping("/test")
    public void runJob() {
        jobService.trigger();
    }
}