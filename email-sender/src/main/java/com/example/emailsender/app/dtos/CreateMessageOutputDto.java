package com.example.emailsender.app.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CreateMessageOutputDto {
    private final String message;
}
