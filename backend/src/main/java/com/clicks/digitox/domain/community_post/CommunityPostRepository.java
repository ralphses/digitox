package com.clicks.digitox.domain.community_post;

import java.util.List;
import java.util.Optional;

public interface CommunityPostRepository {

    Optional<CommunityPost> findById(String id);
    CommunityPost save(CommunityPost post);
    CommunityPost update(CommunityPost post);
    List<CommunityPost> findAll();

}
