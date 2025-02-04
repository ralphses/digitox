package com.clicks.digitox.infrastructure.persistence.milestone;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaDailyMilestoneRepository extends JpaRepository<DailyMilestoneEntity, DailyMilestoneId> {
    @Query("SELECT milestone FROM DailyMilestoneEntity milestone " +
            "WHERE milestone.userEmail = ?1 ")
    List<DailyMilestoneEntity> findAllByUserEmail(String userEmail);

    @Query("SELECT milestone FROM DailyMilestoneEntity milestone " +
            "WHERE milestone.completed = false " +
            "AND milestone.date >= CURRENT_DATE")
    List<DailyMilestoneEntity> findCommunityMilestones();

}
