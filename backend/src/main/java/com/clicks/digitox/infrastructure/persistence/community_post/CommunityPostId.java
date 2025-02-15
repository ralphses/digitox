package com.clicks.digitox.infrastructure.persistence.community_post;

import org.springframework.util.Assert;


public record CommunityPostId(String id) {

    public CommunityPostId {
        Assert.notNull(id, "id can't be null");
    }
}
