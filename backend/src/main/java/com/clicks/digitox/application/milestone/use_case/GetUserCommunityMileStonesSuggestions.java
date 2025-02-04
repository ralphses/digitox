package com.clicks.digitox.application.milestone.use_case;

import com.clicks.digitox.domain.milestone.DailyMilestoneDto;
import com.clicks.digitox.domain.milestone.DailyMilestoneService;
import com.clicks.digitox.domain.user.dto.UserDto;
import com.clicks.digitox.domain.user.service.UserService;
import com.clicks.digitox.shared.annotations.UseCase;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@UseCase
public class GetUserCommunityMileStonesSuggestions {
    private final DailyMilestoneService dailyMilestoneService;
    private final UserService userService;

    public GetUserCommunityMileStonesSuggestions(DailyMilestoneService dailyMilestoneService, UserService userService) {
        this.dailyMilestoneService = dailyMilestoneService;
        this.userService = userService;
    }

    public List<DailyMilestoneDto> execute(String email, int size) {
        // Fetch community milestones and user-specific milestone IDs
        List<DailyMilestoneDto> communityMilestones = dailyMilestoneService.getCommunityMilestones(email);
        UserDto user = userService.findUserByEmail(email);

        List<DailyMilestoneDto> userCommunityMilestones = user.milestones().stream()
                .map(dailyMilestoneService::getById)
                .toList();

        ArrayList<DailyMilestoneDto> dailyMilestoneDtos = new ArrayList<>(communityMilestones);
        dailyMilestoneDtos.addAll(userCommunityMilestones);

        return dailyMilestoneDtos.stream().distinct().toList();
    }

}
