package com.example.emailsender.app.dtos;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class UpdateAttributesDto {

    private Float sparringMinutes;
    private Integer pagesRead;
    private Integer meditationStreak;
    private LocalDate lastMeditation;
    private Integer minutesMeditating;
    private Float kmsRun;
    private Integer minutesRun;

    public UpdateAttributesDto(Float sparringMinutes, Integer pagesRead, Integer meditationStreak, LocalDate lastMeditation, Integer minutesMeditating, Float kmsRun, Integer minutesRun) {
        this.sparringMinutes = sparringMinutes;
        this.pagesRead = pagesRead;
        this.meditationStreak = meditationStreak;
        this.lastMeditation = lastMeditation;
        this.minutesMeditating = minutesMeditating;
        this.kmsRun = kmsRun;
        this.minutesRun = minutesRun;
    }
}
