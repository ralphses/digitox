package com.clicks.digitox.domain.milestone;

import com.clicks.digitox.infrastructure.persistence.milestone.DailyMilestoneId;

import java.util.List;

public interface DailyMilestoneRepository {
    DailyMilestone findById(String id);
    DailyMilestone save(DailyMilestone dailyMilestone);
    List<DailyMilestone> findAllByUserEmail(String userEmail);
    List<DailyMilestone> findCommunityMilestones();
    void complete(String id);
    boolean existsById(String milestoneId);
    void deleteMilestone(DailyMilestoneId dailyMilestoneId);
}
