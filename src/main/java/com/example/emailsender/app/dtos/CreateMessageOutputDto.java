package com.example.emailsender.app.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class CreateMessageOutputDto {
    private final Long id;

    private final String message;
    private final List<CreateAdditionalMessageOutputDto> additionalMessages;

    private final Boolean isActive;
}
