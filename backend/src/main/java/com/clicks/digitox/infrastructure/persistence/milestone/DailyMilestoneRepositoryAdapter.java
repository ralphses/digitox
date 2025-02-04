package com.clicks.digitox.infrastructure.persistence.milestone;

import com.clicks.digitox.domain.milestone.DailyMilestone;
import com.clicks.digitox.domain.milestone.DailyMilestoneRepository;
import com.clicks.digitox.domain.milestone.MilestoneType;
import com.clicks.digitox.domain.milestone.exceptions.MilestoneNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.clicks.digitox.domain.milestone.MilestoneType.COMMUNITY;

@Service
public class DailyMilestoneRepositoryAdapter implements DailyMilestoneRepository {

    private final JpaDailyMilestoneRepository jpaDailyMilestoneRepository;

    public DailyMilestoneRepositoryAdapter(JpaDailyMilestoneRepository jpaDailyMilestoneRepository) {
        this.jpaDailyMilestoneRepository = jpaDailyMilestoneRepository;
    }

    @Override
    public DailyMilestone findById(String id) {

        DailyMilestoneId dailyMilestoneId = getDailyMilestoneId(id);
        System.out.println("dailyMilestoneId = " + dailyMilestoneId);

        DailyMilestone dailyMilestone = jpaDailyMilestoneRepository.findById(dailyMilestoneId)
                .map(JpaMilestoneMapper::toDailyMilestone)
                .orElseThrow(() -> new MilestoneNotFoundException("Invalid Milestone ID: " + id));

        System.out.println("dailyMilestone = " + dailyMilestone);
        return dailyMilestone;
    }

    @Override
    public DailyMilestone save(DailyMilestone dailyMilestone) {
        DailyMilestoneEntity saved = jpaDailyMilestoneRepository.save(
                JpaMilestoneMapper.toDailyMilestoneEntity(dailyMilestone));
        return JpaMilestoneMapper.toDailyMilestone(saved);
    }

    @Override
    public List<DailyMilestone> findAllByUserEmail(String userEmail) {
        return jpaDailyMilestoneRepository.findAllByUserEmail(userEmail)
                .stream()
                .limit(5)
                .map(JpaMilestoneMapper::toDailyMilestone)
                .toList();
    }

    @Override
    public List<DailyMilestone> findCommunityMilestones() {
        return getDailyMilestones(COMMUNITY);
    }

    @Override
    public void complete(String id) {
        DailyMilestoneId dailyMilestoneId = getDailyMilestoneId(id);
        DailyMilestoneEntity dailyMilestoneEntity = jpaDailyMilestoneRepository.findById(dailyMilestoneId)
                .orElseThrow(() -> new MilestoneNotFoundException("Invalid Milestone ID: " + id));
        dailyMilestoneEntity.setCompleted(true);
    }

    @Override
    public boolean existsById(String milestoneId) {
        DailyMilestoneId dailyMilestoneId = getDailyMilestoneId(milestoneId);
        return jpaDailyMilestoneRepository.existsById(dailyMilestoneId);
    }

    @Override
    public void deleteMilestone(DailyMilestoneId dailyMilestoneId) {
        jpaDailyMilestoneRepository.deleteById(dailyMilestoneId);
    }

    private DailyMilestoneId getDailyMilestoneId(String milestoneId) {
        return new DailyMilestoneId(UUID.fromString(milestoneId));
    }

    private List<DailyMilestone> getDailyMilestones(MilestoneType type) {
        return jpaDailyMilestoneRepository.findCommunityMilestones()
                .stream()
                .filter(milestone -> milestone.getType().equals(type))
                .map(JpaMilestoneMapper::toDailyMilestone)
                .toList();
    }
}
