package com.example.emailsender.app.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class InvalidInputException extends RuntimeException {

    private List<?> additionalInformation;

    public InvalidInputException(String errorMessage, List<?> additionalInformation) {
        super(errorMessage);
        this.additionalInformation = additionalInformation;

    }

    public InvalidInputException(String errorMessage) {
        super(errorMessage);

    }
}
