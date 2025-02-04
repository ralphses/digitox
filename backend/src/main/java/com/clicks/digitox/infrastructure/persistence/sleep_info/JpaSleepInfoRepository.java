package com.clicks.digitox.infrastructure.persistence.sleep_info;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public interface JpaSleepInfoRepository extends JpaRepository<SleepInfoEntity, SleepInfoEntityId> {

    Optional<SleepInfoEntity> findByUserEmail(String userEmail);
    Optional<SleepInfoEntity> findByCreatedAtAndUserEmail(LocalDate createdAt, String userEmail);
}
