package com.example.emailsender.app.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class CreateMessageInputDto {

    String message;

    List<CreateAdditionalMessageInputDto> additionalMessages;

    public CreateMessageInputDto(String message, List<CreateAdditionalMessageInputDto> createAdditionalMessages) {
        this.message = message;
        this.additionalMessages = createAdditionalMessages;
    }
}
