package com.clicks.digitox.domain.user.repository;

import com.clicks.digitox.domain.user.dto.UserDto;
import com.clicks.digitox.domain.user.entity.User;

public interface UserRepository {

    User findByUsername(String username);

    UserDto save(User user);

    boolean exists(String userEmail);

    void update(User user);

    long addScreenTIme(String userEmail, long currentScreenTime);
}
