package com.clicks.digitox.application.milestone.ports.api;

import com.clicks.digitox.application.milestone.payloads.CreateMileStonePayload;
import com.clicks.digitox.application.milestone.payloads.JoinCommunityMilestonePayload;
import com.clicks.digitox.shared.utils.ApiResponse;

public interface MilestoneApi {
    ApiResponse getUserCommunitySuggestions(String email);
    ApiResponse getUserMilestones(String userEmail);
    ApiResponse joinCommunityMilestone(JoinCommunityMilestonePayload payload);
    ApiResponse createMilestone(CreateMileStonePayload payload);
    ApiResponse deleteMilestone(String milestoneId);
    ApiResponse getCommunityMilestones();
}
