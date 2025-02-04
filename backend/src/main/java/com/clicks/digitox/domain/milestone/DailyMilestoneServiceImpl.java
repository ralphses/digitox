package com.clicks.digitox.domain.milestone;

import com.clicks.digitox.infrastructure.persistence.milestone.DailyMilestoneId;
import com.clicks.digitox.shared.annotations.ApplicationService;

import java.time.LocalDate;
import java.util.List;

import static com.clicks.digitox.domain.milestone.MilestoneType.COMMUNITY;

@ApplicationService
public class DailyMilestoneServiceImpl implements DailyMilestoneService {

    private final DailyMilestoneRepository dailyMilestoneRepository;

    public DailyMilestoneServiceImpl(DailyMilestoneRepository dailyMilestoneRepository) {
        this.dailyMilestoneRepository = dailyMilestoneRepository;
    }

    @Override
    public List<DailyMilestoneDto> getByUserEmail(String userEmail) {
        return dailyMilestoneRepository.findAllByUserEmail(userEmail)
                .stream()
                .map(MilestoneMapper::milestoneDto)
                .toList();
    }

    @Override
    public List<DailyMilestoneDto> getCommunityMilestones() {
        return dailyMilestoneRepository.findCommunityMilestones()
                .stream()
                .map(MilestoneMapper::milestoneDto)
                .toList();
    }

    @Override
    public List<DailyMilestoneDto> getCommunityMilestones(String userEmail) {
        return getByUserEmail(userEmail).stream()
                .filter(milestone ->  milestone.type().equals(COMMUNITY.name()))
                .toList();
    }

    @Override
    public DailyMilestoneDto create(String userEmail, String label, long maxScreenTime, LocalDate date, MilestoneType type) {
        DailyMilestone newMilestone = new DailyMilestone(userEmail, label, maxScreenTime, date, type);
        var saved = dailyMilestoneRepository.save(newMilestone);
        return MilestoneMapper.milestoneDto(saved);
    }

    @Override
    public boolean existsById(String milestoneId) {
        return dailyMilestoneRepository.existsById(milestoneId);
    }

    @Override
    public void delete(DailyMilestoneId dailyMilestoneId) {
        dailyMilestoneRepository.deleteMilestone(dailyMilestoneId);
    }

    @Override
    public DailyMilestoneDto getById(String milestoneId) {
        DailyMilestone dailyMilestone = dailyMilestoneRepository.findById(milestoneId);
        System.out.println("dailyMilestone = " + dailyMilestone);
        return MilestoneMapper.milestoneDto(dailyMilestone);
    }
}
