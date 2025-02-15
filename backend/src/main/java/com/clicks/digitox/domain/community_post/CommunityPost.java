package com.clicks.digitox.domain.community_post;

import java.time.LocalDateTime;
import java.util.*;

public class CommunityPost {
    private String id;
    private String content;
    private int noOfLikes;
    private Set<String> comments;
    private LocalDateTime createdAt;
    private String createdBy;
    private final String milestoneId;

    public CommunityPost(String content, String createdBy, String milestoneId) {
        this.content = content;
        this.milestoneId = milestoneId;
        this.comments = new HashSet<>();
        this.createdAt = LocalDateTime.now();
        this.createdBy = createdBy;
        this.id = UUID.randomUUID().toString();
    }

    public CommunityPost(String content, String createdBy, String milestoneId, String id) {
        this.content = content;
        this.milestoneId = milestoneId;
        this.comments = new HashSet<>();
        this.createdAt = LocalDateTime.now();
        this.createdBy = createdBy;
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getNoOfLikes() {
        return noOfLikes;
    }

    public void setNoOfLikes(int noOfLikes) {
        this.noOfLikes = noOfLikes;
    }

    public Set<String> getComments() {
        return comments;
    }

    public void setComments(Set<String> comments) {
        this.comments = comments;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getId() {
        return id;
    }

    public void incrementLike() {
        this.noOfLikes++;
    }

    public String getMilestoneId() {
        return milestoneId;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setId(String id) {
        this.id = id;
    }
}
