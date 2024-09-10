package com.example.emailsender.app.controller;

import com.example.emailsender.app.dtos.CreateMessageInputDto;
import com.example.emailsender.app.dtos.ErrorResponseDto;
import com.example.emailsender.app.exceptions.InvalidInputException;
import com.example.emailsender.app.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/create")
    public ResponseEntity<?> createMessage(@RequestBody List<CreateMessageInputDto> message) {
        try {
            return new ResponseEntity<>(emailService.saveMessages(message), HttpStatus.CREATED);
        } catch (InvalidInputException exception) {
            return new ResponseEntity<>(new ErrorResponseDto(exception.getMessage(),
                    exception.getAdditionalInformation().toString()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(emailService.deleteMessageById(id), HttpStatus.OK);
        } catch (InvalidInputException exception) {
            return new ResponseEntity<>(new ErrorResponseDto(exception.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAllData() {
        emailService.deleteAllData();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMessageById(@PathVariable Long id, @RequestBody CreateMessageInputDto updateMessageDto) {
        try {
            return new ResponseEntity<>(emailService.updateMessageById(id, updateMessageDto.getMessage()), HttpStatus.OK);
        } catch (InvalidInputException exception) {
            return new ResponseEntity<>(new ErrorResponseDto(exception.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }
}