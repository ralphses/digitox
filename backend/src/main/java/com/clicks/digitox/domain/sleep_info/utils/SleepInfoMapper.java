package com.clicks.digitox.domain.sleep_info.utils;

import com.clicks.digitox.domain.sleep_info.dto.SleepInfoDto;
import com.clicks.digitox.domain.sleep_info.entity.SleepInfo;

import java.util.ArrayList;
import java.util.List;

public class SleepInfoMapper {

    private SleepInfoMapper() {}

    public static SleepInfoDto toSleepInfoDto(SleepInfo sleepInfo) {
        return new SleepInfoDto(
                formatTime(sleepInfo.getScreenTime()),
                formatTime(sleepInfo.getSleepDuration()),
                sleepInfo.getSleepQuality() + "%"
        );
    }


    public static String formatTime(long totalSeconds) {
        // Calculate hours, minutes, and seconds
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;

        // Build the parts list dynamically
        List<String> parts = new ArrayList<>();
        if (hours > 0) parts.add(hours + "h");
        if (minutes > 0) parts.add(minutes + "m");
        if (seconds > 0 || parts.isEmpty()) parts.add(seconds + "s");

        // Use String.join for efficient concatenation
        return String.join(" ", parts);
    }

}
