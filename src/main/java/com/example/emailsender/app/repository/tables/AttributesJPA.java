package com.example.emailsender.app.repository.tables;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "attributes")
@Getter
@Setter
@NoArgsConstructor
public class AttributesJPA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private float sparringMinutes = 0;

    @Column
    private int pagesRead = 0;

    @Column
    private int meditationStreak = 0;

    @Column
    private LocalDate lastMeditation;

    @Column
    private int minutesMeditating = 0;

    @Column
    private float kmsRun = 0;

    @Column
    private int minutesRun = 0;
}
