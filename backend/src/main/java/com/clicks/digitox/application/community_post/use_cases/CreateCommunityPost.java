package com.clicks.digitox.application.community_post.use_cases;

import com.clicks.digitox.application.community_post.payload.CreateCommunityPostPayload;
import com.clicks.digitox.domain.community_post.CommunityPost;
import com.clicks.digitox.domain.community_post.CommunityPostDto;
import com.clicks.digitox.domain.community_post.CommunityPostMapper;
import com.clicks.digitox.domain.community_post.CommunityPostService;
import com.clicks.digitox.domain.community_post.exceptions.InvalidMilestoneIdException;
import com.clicks.digitox.domain.milestone.DailyMilestoneService;
import com.clicks.digitox.shared.annotations.UseCase;

import java.util.UUID;

import static com.clicks.digitox.domain.community_post.CommunityPostMapper.communityPostDto;

@UseCase
public class CreateCommunityPost {

    private final CommunityPostService communityPostService;
    private final DailyMilestoneService dailyMilestoneService;

    public CreateCommunityPost(CommunityPostService communityPostService, DailyMilestoneService dailyMilestoneService) {
        this.communityPostService = communityPostService;
        this.dailyMilestoneService = dailyMilestoneService;
    }

    public CommunityPostDto create(CreateCommunityPostPayload payload) {

        if (dailyMilestoneService.existsById(payload.milestoneId())) {
            CommunityPost savedPost = communityPostService.create(
                    payload.content(),
                    payload.milestoneId(),
                    payload.userEmail()
            );
            return communityPostDto(savedPost);
        }
        throw new InvalidMilestoneIdException();

    }
}
