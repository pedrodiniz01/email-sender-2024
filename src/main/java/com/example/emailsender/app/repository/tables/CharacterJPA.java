package com.example.emailsender.app.repository.tables;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "characters")
@Getter
@Setter
@NoArgsConstructor
public class CharacterJPA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String gender;

    @Column
    private LocalDate birthDate;

    @Column(nullable = false)
    private LocalDate date = LocalDate.now();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "attributes_id", referencedColumnName = "id")
    private AttributesJPA attributes = new AttributesJPA();
}
