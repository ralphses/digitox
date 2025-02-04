package com.clicks.digitox.application.user.payloads;

import com.clicks.digitox.domain.sleep_info.dto.SleepInfoDto;
import com.clicks.digitox.domain.user.dto.UserDto;

public record LoginResponse(UserDto user, SleepInfoDto stats, String token) {
}
