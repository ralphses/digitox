package com.clicks.digitox.application.community_post.use_cases;

import com.clicks.digitox.domain.community_post.CommunityPost;
import com.clicks.digitox.domain.community_post.CommunityPostService;
import com.clicks.digitox.shared.annotations.UseCase;

@UseCase
public class AddLike {

    private final CommunityPostService communityPostService;

    public AddLike(CommunityPostService communityPostService) {
        this.communityPostService = communityPostService;
    }

    public void execute(String postId) {
        CommunityPost post = communityPostService.findById(postId);
        communityPostService.incrementLike(post);
    }
}
