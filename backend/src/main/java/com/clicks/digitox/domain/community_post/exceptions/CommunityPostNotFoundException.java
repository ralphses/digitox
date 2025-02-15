package com.clicks.digitox.domain.community_post.exceptions;

public class CommunityPostNotFoundException extends RuntimeException{
    public CommunityPostNotFoundException() {
        super("No community post found");
    }
}
