package com.clicks.digitox.domain.user.entity;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String name;
    private String email;
    private String password;
    private String profileImage;
    private String bannerImage;
    private UserRole role;
    private boolean active;
    private Long totalScreenTime;
    private Long points;
    private UserLevel level;
    private List<String> milestones;

    public List<String> getMilestones() {
        return milestones;
    }

    public void setMilestones(List<String> milestones) {
        this.milestones = milestones;
    }

    public Long getTotalScreenTime() {
        return totalScreenTime;
    }

    public void setTotalScreenTime(Long totalScreenTime) {
        this.totalScreenTime = totalScreenTime;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

    public UserLevel getLevel() {
        return level;
    }

    public void setLevel(UserLevel level) {
        this.level = level;
    }

    public User(String name, String email, String password, String profileImage, String bannerImage, UserRole role, boolean active, Long totalScreenTime, Long points, UserLevel level) {
        this(name, email, password, profileImage, bannerImage, role);

        this.active = active;
        this.totalScreenTime = totalScreenTime;
        this.points = points;
        this.level = level;
    }


    public User(String name, String email, String password, String profileImage, String bannerImage, UserRole role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.profileImage = profileImage;
        this.bannerImage = bannerImage;
        this.role = role;
        this.active = true;
        this.milestones = new ArrayList<>();
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
