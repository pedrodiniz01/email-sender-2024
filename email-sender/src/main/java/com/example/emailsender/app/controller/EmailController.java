package com.example.emailsender.app.controller;

import com.example.emailsender.app.Utils.ListUtils;
import com.example.emailsender.app.dtos.CreateMessageInputDto;
import com.example.emailsender.app.dtos.ErrorResponseDto;
import com.example.emailsender.app.exceptions.InvalidMessageException;
import com.example.emailsender.app.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody List<CreateMessageInputDto> message) {
        try {
            return new ResponseEntity<>(emailService.saveMessages(message), HttpStatus.CREATED);
        } catch (InvalidMessageException exception) {
            return new ResponseEntity<>(new ErrorResponseDto(exception.getMessage(),
                    exception.getAdditionalInformation().toString()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAllData() {
        emailService.deleteAllData();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}