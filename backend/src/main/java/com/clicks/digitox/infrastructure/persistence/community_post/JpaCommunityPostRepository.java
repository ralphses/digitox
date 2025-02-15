package com.clicks.digitox.infrastructure.persistence.community_post;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCommunityPostRepository extends JpaRepository<CommunityPostEntity, CommunityPostId> {

}
