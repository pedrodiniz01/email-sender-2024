package com.example.emailsender.app.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class InvalidInputException extends RuntimeException {

    private List<String> additionalInformation;

    public InvalidInputException(String errorMessage, List<String> additionalInformation) {
        super(errorMessage);
        this.additionalInformation = additionalInformation;

    }

    public InvalidInputException(String errorMessage) {
        super(errorMessage);

    }
}
