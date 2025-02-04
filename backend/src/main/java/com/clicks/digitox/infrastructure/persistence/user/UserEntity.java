package com.clicks.digitox.infrastructure.persistence.user;

import com.clicks.digitox.domain.user.entity.UserLevel;
import com.clicks.digitox.domain.user.entity.UserRole;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class UserEntity {

    @EmbeddedId
    private UserId id;

    private String name;

    @Column(unique = true)
    private String email;
    private String password;
    private String profileImage;
    private String bannerImage;
    private boolean active;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private Long totalScreenTime;
    private Long points;

    @Enumerated(EnumType.STRING)
    private UserLevel level;

    @ElementCollection
    private List<String> milestones;

    protected UserEntity(){}

    public UserEntity(String name, String email, String password, String profileImage, String bannerImage, boolean active, UserRole role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.profileImage = profileImage;
        this.bannerImage = bannerImage;
        this.active = active;
        this.role = role;
        this.level = UserLevel.BEGINNER;
        this.totalScreenTime = 0L;
        this.points = 0L;
        this.id = new UserId();
        this.milestones = new ArrayList<>();
    }

    public UserId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isActive() {
        return active;
    }

    public UserRole getRole() {
        return role;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public Long getTotalScreenTime() {
        return totalScreenTime;
    }

    public Long getPoints() {
        return points;
    }

    public UserLevel getLevel() {
        return level;
    }

    public List<String> getMilestones() {
        return milestones;
    }

    public void addScreenTime(long currentScreenTime) {
        totalScreenTime += currentScreenTime;
    }
}
