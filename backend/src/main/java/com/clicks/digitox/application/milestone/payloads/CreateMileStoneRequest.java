package com.clicks.digitox.application.milestone.payloads;

public record CreateMileStoneRequest(
        String userEmail,
        String label,
        long maxScreenTime,
        String date,
        String type
) {
}
