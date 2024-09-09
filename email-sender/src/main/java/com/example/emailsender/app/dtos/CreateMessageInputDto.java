package com.example.emailsender.app.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class CreateMessageInputDto {

    String message;

    public CreateMessageInputDto(String message) {
        this.message = message;
    }
}
