package com.example.emailsender.app.repository;

import com.example.emailsender.app.repository.tables.MessageCounterJpa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageCounterJpaRepository extends JpaRepository<MessageCounterJpa, Long> {


}
