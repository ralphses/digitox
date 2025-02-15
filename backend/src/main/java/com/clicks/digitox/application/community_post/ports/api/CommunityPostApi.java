package com.clicks.digitox.application.community_post.ports.api;

import com.clicks.digitox.application.community_post.payload.AddCommentPayload;
import com.clicks.digitox.application.community_post.payload.CreateCommunityPostPayload;
import com.clicks.digitox.shared.utils.ApiResponse;

public interface CommunityPostApi {
    ApiResponse addComment(AddCommentPayload payload);
    ApiResponse incrementLike(String postId);
    ApiResponse create(CreateCommunityPostPayload communityPost);
    ApiResponse findAll();
}
