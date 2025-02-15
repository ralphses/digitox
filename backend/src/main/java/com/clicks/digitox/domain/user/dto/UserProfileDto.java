package com.clicks.digitox.domain.user.dto;

import com.clicks.digitox.domain.milestone.DailyMilestoneDto;
import com.clicks.digitox.domain.sleep_info.dto.SleepInfoDto;

import java.util.List;

public record UserProfileDto(UserDto user, SleepInfoDto stats, List<DailyMilestoneDto> milestones) {
}
