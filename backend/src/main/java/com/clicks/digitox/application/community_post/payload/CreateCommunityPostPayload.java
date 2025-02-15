package com.clicks.digitox.application.community_post.payload;

public record CreateCommunityPostPayload(
        String userEmail,
        String content,
        String milestoneId
) {
}
