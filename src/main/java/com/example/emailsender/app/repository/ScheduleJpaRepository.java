package com.example.emailsender.app.repository;

import com.example.emailsender.app.repository.tables.ScheduleJpa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleJpaRepository  extends JpaRepository<ScheduleJpa, Long> {

    boolean existsByHour(Integer hour);
}
