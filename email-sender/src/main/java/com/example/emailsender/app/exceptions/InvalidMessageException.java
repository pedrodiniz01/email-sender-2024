package com.example.emailsender.app.exceptions;

public class InvalidMessageException extends RuntimeException {

    public InvalidMessageException(String errorMessage) {
        super(errorMessage);
    }
}
