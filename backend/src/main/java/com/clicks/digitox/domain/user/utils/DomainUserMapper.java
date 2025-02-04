package com.clicks.digitox.domain.user.utils;

import com.clicks.digitox.domain.sleep_info.utils.SleepInfoMapper;
import com.clicks.digitox.domain.user.dto.UserDto;
import com.clicks.digitox.domain.user.entity.User;

import static com.clicks.digitox.domain.sleep_info.utils.SleepInfoMapper.formatTime;

public class DomainUserMapper {

    private DomainUserMapper() {}

    public static UserDto userDto(User user) {
        return new UserDto(
                user.getName(),
                user.getEmail(),
                user.getRole().name(),
                user.getProfileImage(),
                user.getBannerImage(),
                user.isActive(),
                formatTime(user.getTotalScreenTime()),
                user.getPoints(),
                user.getLevel(),
                user.getMilestones()
        );
    }
}
