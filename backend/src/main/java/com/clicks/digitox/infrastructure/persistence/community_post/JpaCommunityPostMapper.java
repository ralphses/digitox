package com.clicks.digitox.infrastructure.persistence.community_post;

import com.clicks.digitox.domain.community_post.CommunityPost;

public class JpaCommunityPostMapper {

    private JpaCommunityPostMapper() {}

    public static CommunityPost communityPost(CommunityPostEntity communityPost) {
        CommunityPost foundPost = new CommunityPost(communityPost.getContent(), communityPost.getCreatedBy(), communityPost.getMilestoneId(), communityPost.getId().id());
        foundPost.setComments(communityPost.getComments());
        foundPost.setNoOfLikes(communityPost.getNoOfLikes());
        foundPost.setCreatedAt(communityPost.getCreatedAt());
        foundPost.setId(foundPost.getId());
        return foundPost;
    }

    public static CommunityPostEntity communityPostEntity(CommunityPost communityPost) {
        CommunityPostId id = new CommunityPostId(communityPost.getId());
        CommunityPostEntity communityPostEntity = new CommunityPostEntity(
                id,
                communityPost.getContent(),
                communityPost.getCreatedBy(),
                communityPost.getMilestoneId()
        );
        communityPostEntity.setComments(communityPost.getComments());
        communityPostEntity.setNoOfLikes(communityPost.getNoOfLikes());
        communityPostEntity.setCreatedAt(communityPost.getCreatedAt());
        return communityPostEntity;
    }
}
