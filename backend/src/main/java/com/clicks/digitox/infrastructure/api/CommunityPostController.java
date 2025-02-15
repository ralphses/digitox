package com.clicks.digitox.infrastructure.api;

import com.clicks.digitox.application.community_post.payload.AddCommentPayload;
import com.clicks.digitox.application.community_post.payload.CreateCommunityPostPayload;
import com.clicks.digitox.application.community_post.ports.api.CommunityPostApi;
import com.clicks.digitox.application.community_post.use_cases.AddComment;
import com.clicks.digitox.application.community_post.use_cases.AddLike;
import com.clicks.digitox.application.community_post.use_cases.CreateCommunityPost;
import com.clicks.digitox.application.community_post.use_cases.GetAllCommunityPost;
import com.clicks.digitox.domain.community_post.CommunityPostDto;
import com.clicks.digitox.shared.utils.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("api/v1/milestone/post")
public class CommunityPostController implements CommunityPostApi {

    private final AddComment addComment;
    private final CreateCommunityPost createCommunityPost;
    private final AddLike addLike;
    private final GetAllCommunityPost getAllCommunityPost;

    public CommunityPostController(AddComment addComment, CreateCommunityPost createCommunityPost, AddLike addLike, GetAllCommunityPost getAllCommunityPost) {
        this.addComment = addComment;
        this.createCommunityPost = createCommunityPost;
        this.addLike = addLike;
        this.getAllCommunityPost = getAllCommunityPost;
    }


    @Override
    @PostMapping("comment")
    public ApiResponse addComment(@RequestBody AddCommentPayload payload) {
        addComment.execute(payload);
        return new ApiResponse(true, Collections.emptyList());
    }

    @Override
    @PostMapping("{postId}/like")
    public ApiResponse incrementLike(@PathVariable String postId) {
        addLike.execute(postId);
        return new ApiResponse(true, Collections.emptyList());
    }

    @Override
    @PostMapping
    public ApiResponse create(@RequestBody CreateCommunityPostPayload payload) {
        CommunityPostDto created = createCommunityPost.create(payload);
        return new ApiResponse(true, created);

    }

    @Override
    @GetMapping
    public ApiResponse findAll() {
        List<CommunityPostDto> communityPosts = getAllCommunityPost.execute();
        return new ApiResponse(true, communityPosts);
    }
}
