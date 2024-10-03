package com.example.emailsender.app.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class CreateAdditionalMessageOutputDto {

    Long id;
    LocalDate date;
    String additionalMessage;

    public CreateAdditionalMessageOutputDto(Long id, String additionalMessage, LocalDate date) {
        this.id = id;
        this.additionalMessage = additionalMessage;
        this.date = date;
    }
}
