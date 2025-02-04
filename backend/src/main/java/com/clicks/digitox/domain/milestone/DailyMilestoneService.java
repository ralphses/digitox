package com.clicks.digitox.domain.milestone;

import com.clicks.digitox.infrastructure.persistence.milestone.DailyMilestoneId;

import java.time.LocalDate;
import java.util.List;

public interface DailyMilestoneService {

    List<DailyMilestoneDto> getByUserEmail(String userEmail);

    List<DailyMilestoneDto> getCommunityMilestones();
    List<DailyMilestoneDto> getCommunityMilestones(String userEmail);

    DailyMilestoneDto create(String userEmail, String label, long maxScreenTime, LocalDate date, MilestoneType type);

    boolean existsById(String milestoneId);

    void delete(DailyMilestoneId dailyMilestoneId);

    DailyMilestoneDto getById(String milestoneId);
}
