package com.example.emailsender.app.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ScheduleDto {
    private final Long id;
    private final Integer hour;
}
