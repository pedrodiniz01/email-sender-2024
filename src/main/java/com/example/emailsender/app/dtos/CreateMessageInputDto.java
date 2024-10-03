package com.example.emailsender.app.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class CreateMessageInputDto {

    String mainMessage;

    List<CreateAdditionalMessageInputDto> additionalMessages;

    public CreateMessageInputDto(String mainMessage, List<CreateAdditionalMessageInputDto> createAdditionalMessages) {
        this.mainMessage = mainMessage;
        this.additionalMessages = createAdditionalMessages;
    }
}
