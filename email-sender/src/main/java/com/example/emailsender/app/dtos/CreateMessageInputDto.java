package com.example.emailsender.app.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CreateMessageInputDto {

    String message;

    public CreateMessageInputDto(String message) {
        this.message = message;
    }
}
