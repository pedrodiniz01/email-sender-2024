package com.example.emailsender.app.repository.tables;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "additionalMessages")
@Getter
@Setter
@NoArgsConstructor
public class AdditionalMessageJPA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String additionalMessage;
    
    private LocalDate date = LocalDate.now();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id", nullable = false)
    private MessageJpa messageJpa;

    public Long getMessageId() {
        return messageJpa != null ? messageJpa.getId() : null;
    }
}
