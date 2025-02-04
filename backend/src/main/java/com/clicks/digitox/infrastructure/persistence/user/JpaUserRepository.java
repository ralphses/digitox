package com.clicks.digitox.infrastructure.persistence.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<UserEntity, UserId> {

    Optional<UserEntity> findByEmail(String email);
    boolean existsByEmail(String email);
}
