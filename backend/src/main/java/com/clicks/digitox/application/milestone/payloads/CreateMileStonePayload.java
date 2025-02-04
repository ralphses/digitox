package com.clicks.digitox.application.milestone.payloads;

public record CreateMileStonePayload(String userEmail, String label, long maxScreenTime, String date, String type) {
}
