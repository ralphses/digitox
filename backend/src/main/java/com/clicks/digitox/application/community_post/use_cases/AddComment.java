package com.clicks.digitox.application.community_post.use_cases;

import com.clicks.digitox.application.community_post.payload.AddCommentPayload;
import com.clicks.digitox.domain.community_post.CommunityPost;
import com.clicks.digitox.domain.community_post.CommunityPostService;
import com.clicks.digitox.shared.annotations.UseCase;

@UseCase
public class AddComment {

    private final CommunityPostService communityPostService;

    public AddComment(CommunityPostService communityPostService) {
        this.communityPostService = communityPostService;
    }

    public void execute(AddCommentPayload payload) {
        CommunityPost post = communityPostService.findById(payload.postId());
        communityPostService.addComment(post, payload.comment());
    }
}
