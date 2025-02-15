package com.clicks.digitox.domain.community_post;

import java.time.format.DateTimeFormatter;

public class CommunityPostMapper {

    private CommunityPostMapper() {}

    public static CommunityPostDto communityPostDto(CommunityPost post) {
        return new CommunityPostDto(
                post.getId(),
                post.getContent(),
                post.getNoOfLikes(),
                post.getComments(),
                post.getCreatedAt().format(DateTimeFormatter.ofPattern("MMMM d, yyyy h:mma")),
                post.getCreatedBy()
                );
    }
}
