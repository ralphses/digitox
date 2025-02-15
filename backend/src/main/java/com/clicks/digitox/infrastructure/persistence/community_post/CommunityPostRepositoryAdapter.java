package com.clicks.digitox.infrastructure.persistence.community_post;

import com.clicks.digitox.domain.community_post.CommunityPost;
import com.clicks.digitox.domain.community_post.CommunityPostRepository;
import com.clicks.digitox.domain.community_post.exceptions.CommunityPostNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

import static com.clicks.digitox.infrastructure.persistence.community_post.JpaCommunityPostMapper.*;

@Component
public class CommunityPostRepositoryAdapter implements CommunityPostRepository {

    private final JpaCommunityPostRepository communityPostRepository;

    public CommunityPostRepositoryAdapter(JpaCommunityPostRepository communityPostRepository) {
        this.communityPostRepository = communityPostRepository;
    }

    @Override
    public Optional<CommunityPost> findById(String id) {
        return communityPostRepository.findById(new CommunityPostId(id))
                .map(JpaCommunityPostMapper::communityPost);
    }

    @Override
    public CommunityPost save(CommunityPost post) {
        return communityPost(communityPostRepository.save(communityPostEntity(post)));
    }

    @Override
    @Transactional
    public CommunityPost update(CommunityPost post) {
        CommunityPostEntity entity = findEntityById(post.getId());
        entity.getComments().addAll(post.getComments());
        entity.setNoOfLikes(entity.getNoOfLikes() + post.getNoOfLikes());
        entity.setContent(post.getContent());
        return communityPost(entity);
    }

    private CommunityPostEntity findEntityById(String id) {
        return communityPostRepository.findById(new CommunityPostId(id))
                .orElseThrow(CommunityPostNotFoundException::new);
    }

    @Override
    public List<CommunityPost> findAll() {
        return communityPostRepository.findAll().stream().map(JpaCommunityPostMapper::communityPost).toList();
    }
}