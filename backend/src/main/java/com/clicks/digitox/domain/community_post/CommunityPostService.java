package com.clicks.digitox.domain.community_post;

import java.util.List;
import java.util.UUID;

public interface CommunityPostService {

    CommunityPost findById(String postId);

    void addComment(CommunityPost post, String comment);

    void incrementLike(CommunityPost post);

    CommunityPost create(String content, String milestoneId, String userEmail);

    List<CommunityPost> findAll();
}

