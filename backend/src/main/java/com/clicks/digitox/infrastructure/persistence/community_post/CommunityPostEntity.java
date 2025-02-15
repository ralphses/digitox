package com.clicks.digitox.infrastructure.persistence.community_post;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class CommunityPostEntity {

    @EmbeddedId
    private CommunityPostId id;
    private String content;
    private int noOfLikes;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> comments;
    private LocalDateTime createdAt;
    private String createdBy;
    private String milestoneId;

    protected CommunityPostEntity() {
        this.createdAt = LocalDateTime.now();
        this.comments = new HashSet<>();
    }

    public CommunityPostEntity(CommunityPostId id, String content, String userEmail, String milestoneId) {
        this();
        this.id = id;
        this.content = content;
        this.milestoneId = milestoneId;
        this.createdBy = userEmail;
    }

    public CommunityPostId getId() {
        return id;
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

    public String getMilestoneId() {
        return milestoneId;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setId(CommunityPostId id) {
        this.id = id;
    }
}
