package com.example.emailsender.app.controller;

import com.example.emailsender.app.dtos.CreateMessageInputDto;
import com.example.emailsender.app.dtos.ErrorResponseDto;
import com.example.emailsender.app.exceptions.InvalidMessageException;
import com.example.emailsender.app.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody CreateMessageInputDto message) {
        try {
            return new ResponseEntity<>(emailService.saveMessage(message), HttpStatus.CREATED);
        } catch (InvalidMessageException exception) {
            return new ResponseEntity<>(new ErrorResponseDto(exception.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
