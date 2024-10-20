package com.example.emailsender.app.controller;

import com.example.emailsender.app.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/job")
public class JobController {
    @Autowired
    JobService jobService;

    @PostMapping("/trigger")
    public ResponseEntity<?> triggerRandomEmail() {
        jobService.trigger();
        return new ResponseEntity<>("Job triggered.", HttpStatus.OK);
    }

    @PostMapping("/attributes")
    public ResponseEntity<?> triggerAttributes() {
        jobService.sendAttributesEmail();
        return new ResponseEntity<>("Job triggered.", HttpStatus.OK);
    }
}
