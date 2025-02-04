package com.clicks.digitox.domain.milestone;


public record DailyMilestoneDto(
        String id,
        String userEmail,
        String label,
        int progress,
        boolean completed,
        long maxScreenTime,
        String date,
        String type
) {
}