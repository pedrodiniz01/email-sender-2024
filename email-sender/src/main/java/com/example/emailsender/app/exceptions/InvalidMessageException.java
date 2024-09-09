package com.example.emailsender.app.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class InvalidMessageException extends RuntimeException {

    private List<String> additionalInformation;

    public InvalidMessageException(String errorMessage, List<String> additionalInformation) {
        super(errorMessage);
        this.additionalInformation = additionalInformation;

    }
}
