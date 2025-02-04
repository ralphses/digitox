package com.clicks.digitox.domain.user.dto;

import com.clicks.digitox.domain.sleep_info.dto.SleepInfoDto;

public record UserProfileDto(UserDto user, SleepInfoDto stats) {
}
