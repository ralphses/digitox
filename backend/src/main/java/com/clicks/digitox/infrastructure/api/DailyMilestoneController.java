package com.clicks.digitox.infrastructure.api;

import com.clicks.digitox.application.milestone.payloads.CreateMileStonePayload;
import com.clicks.digitox.application.milestone.payloads.JoinCommunityMilestonePayload;
import com.clicks.digitox.application.milestone.ports.api.MilestoneApi;
import com.clicks.digitox.application.milestone.use_case.*;
import com.clicks.digitox.domain.milestone.DailyMilestoneDto;
import com.clicks.digitox.domain.milestone.MilestoneType;
import com.clicks.digitox.infrastructure.persistence.milestone.DailyMilestoneId;
import com.clicks.digitox.shared.utils.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/milestone")
public class DailyMilestoneController implements MilestoneApi {

    private final GetUserCommunityMileStonesSuggestions getUserCommunityMileStonesSuggestions;
    private final GetUserDailyMileStones getUserDailyMileStones;
    private final JoinCommunityMilestone joinCommunityMilestone;
    private final CreateNewDailyMilestone createNewDailyMilestone;
    private final DeleteMileStone deleteMileStone;
    private final GetAllActiveCommunityMilestones getAllActiveCommunityMilestones;

    public DailyMilestoneController(GetUserCommunityMileStonesSuggestions getUserCommunityMileStonesSuggestions, GetUserDailyMileStones getUserDailyMileStones, JoinCommunityMilestone joinCommunityMilestone, CreateNewDailyMilestone createNewDailyMilestone, DeleteMileStone deleteMileStone, GetAllActiveCommunityMilestones getAllActiveCommunityMilestones) {
        this.getUserCommunityMileStonesSuggestions = getUserCommunityMileStonesSuggestions;
        this.getUserDailyMileStones = getUserDailyMileStones;
        this.joinCommunityMilestone = joinCommunityMilestone;
        this.createNewDailyMilestone = createNewDailyMilestone;
        this.deleteMileStone = deleteMileStone;
        this.getAllActiveCommunityMilestones = getAllActiveCommunityMilestones;
    }

    @Override
    @GetMapping("community/{email}")
    public ApiResponse getUserCommunitySuggestions(@PathVariable String email) {
        List<DailyMilestoneDto> milestones = getUserCommunityMileStonesSuggestions.execute(email, 3);
        return new ApiResponse(true, milestones);
    }

    @Override
    @GetMapping("user/{email}")
    public ApiResponse getUserMilestones(@PathVariable String email) {
        List<DailyMilestoneDto> milestones = getUserDailyMileStones.execute(email);
        return new ApiResponse(true, milestones);
    }

    /**
     * Join a community milestone for users
     * @param payload {@link JoinCommunityMilestonePayload} containing all details of user and the milestone they want to join
     * @return {@link ApiResponse} - a wrapper containing the status of the join request
     */
    @Override
    @PostMapping("community")
    public ApiResponse joinCommunityMilestone(@RequestBody JoinCommunityMilestonePayload payload) {
        boolean joined = joinCommunityMilestone.execute(payload.userEmail(), payload.milestoneId());
        return new ApiResponse(true, joined);
    }

    @Override
    @PostMapping
    public ApiResponse createMilestone(@RequestBody CreateMileStonePayload payload) {
        DailyMilestoneDto milestone = createNewDailyMilestone.execute(
                payload.userEmail(),
                payload.label(),
                payload.maxScreenTime(),
                LocalDate.parse(payload.date()),
                MilestoneType.valueOf(payload.type().toUpperCase())
        );
        return new ApiResponse(true, milestone);
    }

    @Override
    @DeleteMapping("{milestoneId}")
    public ApiResponse deleteMilestone(@PathVariable String milestoneId) {
        var dailyMilestoneId = new DailyMilestoneId(milestoneId);
        deleteMileStone.execute(dailyMilestoneId);
        return new ApiResponse(true, Collections.emptyList());
    }

    @Override
    @GetMapping
    public ApiResponse getCommunityMilestones() {
        List<DailyMilestoneDto> communityMilestones = getAllActiveCommunityMilestones.execute();
        return new ApiResponse(true, communityMilestones);
    }
}
