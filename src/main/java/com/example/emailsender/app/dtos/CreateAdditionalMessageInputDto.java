package com.example.emailsender.app.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CreateAdditionalMessageInputDto {

    String additionalMessage;

    public CreateAdditionalMessageInputDto(String additionalMessage) {
        this.additionalMessage = additionalMessage;
    }
}
