package com.clicks.digitox.domain.community_post;

import java.util.Set;

public record CommunityPostDto(
        String id,
        String content,
        int noOfLikes,
        Set<String> comments,
        String createdAt,
        String createdBy
) {
}
