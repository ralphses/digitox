package com.clicks.digitox.domain.community_post.exceptions;

public class InvalidMilestoneIdException extends RuntimeException{
    public InvalidMilestoneIdException() {
        super("Invalid milestone id");
    }
}
