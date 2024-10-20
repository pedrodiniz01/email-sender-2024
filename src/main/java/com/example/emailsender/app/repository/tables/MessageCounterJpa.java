package com.example.emailsender.app.repository.tables;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "messageCounter")
@Getter
@Setter
@NoArgsConstructor
public class MessageCounterJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String message;

    @Column
    private Integer counter;
}
