package com.example.emailsender.app.controller;

import com.example.emailsender.app.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sender")
public class SenderController {
    @Autowired
    EmailSenderService senderService;

    @PostMapping("/trigger")
    public ResponseEntity<?> sendEmail() {
        senderService.sendRandomMessageEmail();
        return new ResponseEntity<>("Email triggered.", HttpStatus.OK);
    }

    @PostMapping("/backup")
    public ResponseEntity<?> sendBackupEmail() {
        senderService.sendBackupEmail();
        return new ResponseEntity<>("Backup email triggered.", HttpStatus.OK);
    }
}
