package com.example.emailsender.app.repository;

import com.example.emailsender.app.repository.tables.CharacterJPA;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterJpaRepository extends JpaRepository<CharacterJPA, Long> {
}
