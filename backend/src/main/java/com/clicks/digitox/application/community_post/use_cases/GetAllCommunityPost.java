package com.clicks.digitox.application.community_post.use_cases;

import com.clicks.digitox.domain.community_post.CommunityPost;
import com.clicks.digitox.domain.community_post.CommunityPostDto;
import com.clicks.digitox.domain.community_post.CommunityPostMapper;
import com.clicks.digitox.domain.community_post.CommunityPostService;
import com.clicks.digitox.shared.annotations.UseCase;

import java.util.List;

@UseCase
public class GetAllCommunityPost {

    private final CommunityPostService communityPostService;

    public GetAllCommunityPost(CommunityPostService communityPostService) {
        this.communityPostService = communityPostService;
    }

    public List<CommunityPostDto> execute() {
        List<CommunityPost> communityPosts = communityPostService.findAll();
        return communityPosts.stream().map(CommunityPostMapper::communityPostDto).toList();
    }
}
