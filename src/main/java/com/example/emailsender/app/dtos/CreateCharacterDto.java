package com.example.emailsender.app.dtos;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class CreateCharacterDto {

    private String name;

    private String gender;

    private LocalDate birthDate;

    public CreateCharacterDto(String name, String gender, LocalDate birthdate) {
        this.name = name;
        this.gender = gender;
        this.birthDate = birthdate;
    }
}
