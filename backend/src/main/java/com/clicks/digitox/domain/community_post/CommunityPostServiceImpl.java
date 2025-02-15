package com.clicks.digitox.domain.community_post;

import com.clicks.digitox.domain.community_post.exceptions.CommunityPostNotFoundException;
import com.clicks.digitox.shared.annotations.ApplicationService;

import java.util.List;

@ApplicationService
public class CommunityPostServiceImpl implements CommunityPostService {

    private final CommunityPostRepository communityPostRepository;

    public CommunityPostServiceImpl(CommunityPostRepository communityPostRepository) {
        this.communityPostRepository = communityPostRepository;
    }

    @Override
    public CommunityPost findById(String postId) {
        return communityPostRepository.findById(postId)
                .orElseThrow(CommunityPostNotFoundException::new);
    }

    @Override
    public void addComment(CommunityPost post, String comment) {
        post.getComments().add(comment);
        communityPostRepository.save(post);
    }

    @Override
    public void incrementLike(CommunityPost post) {
        post.incrementLike();
        communityPostRepository.save(post);
    }

    @Override
    public CommunityPost create(String content, String milestoneId, String userEmail) {
        CommunityPost communityPost = new CommunityPost(
                content,
                userEmail,
                milestoneId
        );
        return communityPostRepository.save(communityPost);
    }

    @Override
    public List<CommunityPost> findAll() {
        return communityPostRepository.findAll();
    }
}
