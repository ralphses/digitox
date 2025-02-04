package com.clicks.digitox.domain.user.service;

import com.clicks.digitox.domain.user.dto.UserDto;

public interface UserService {
    UserDto createNewUser(String name, String email, String encodedPassword);
    UserDto findUserByEmail(String email);

    boolean addMilestoneToUser(String milestoneId, String userEmail);

    boolean exists(String userEmail);

    String updateTotalScreenTime(String userEmail, long currentScreenTime);
}
