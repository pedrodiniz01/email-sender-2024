package com.example.emailsender.app.repository;

import com.example.emailsender.app.repository.tables.AdditionalMessageJPA;
import com.example.emailsender.app.repository.tables.MessageJpa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdditionalMessageJpaRepository extends JpaRepository<AdditionalMessageJPA, Long> {
}