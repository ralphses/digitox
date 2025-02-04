package com.clicks.digitox.domain.user.dto;

import com.clicks.digitox.domain.user.entity.UserLevel;

import java.util.List;

public record UserDto(
        String name,
        String email,
        String role,
        String profileImage,
        String bannerImage,
        boolean active,
        String totalScreenTime,
        Long points,
        UserLevel level,
        List<String> milestones
        ) {
}
