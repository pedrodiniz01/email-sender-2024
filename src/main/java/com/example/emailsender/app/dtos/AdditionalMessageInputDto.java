package com.example.emailsender.app.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class AdditionalMessageInputDto {

    String message;


    public AdditionalMessageInputDto(String message) {
        this.message = message;
    }
}
