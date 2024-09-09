package com.example.emailsender.app.repository;

import com.example.emailsender.app.repository.tables.MessageJpa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageJpaRepository extends JpaRepository<MessageJpa, Long> {
    boolean existsByMessage(String message);
}