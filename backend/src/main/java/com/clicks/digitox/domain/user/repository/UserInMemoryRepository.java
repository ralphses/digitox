package com.clicks.digitox.domain.user.repository;

public interface UserInMemoryRepository {

    void addUserId(String email);
    boolean userIdExists(String email);
    boolean removeUserId(String email);
}
