package com.example.emailsender.app.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@RequiredArgsConstructor
public class CreateMessageOutputDto {
    private final Long id;
    private final String message;
    private final LocalDate date;
}
