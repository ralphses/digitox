package com.clicks.digitox.infrastructure.persistence.user;

import com.clicks.digitox.domain.user.dto.UserDto;
import com.clicks.digitox.domain.user.entity.User;

public class UserMapper {

    private UserMapper(){}

    public static User toUser(UserEntity userEntity) {
        User user = new User(
                userEntity.getName(),
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.getProfileImage(),
                userEntity.getBannerImage(),
                userEntity.getRole()
        );
        user.setMilestones(userEntity.getMilestones());
        user.setTotalScreenTime(userEntity.getTotalScreenTime());
        user.setPoints(userEntity.getPoints());
        user.setLevel(userEntity.getLevel());
        return user;
    }

    public static UserDto toUserDto(UserEntity userEntity) {
        return new UserDto(
                userEntity.getName(),
                userEntity.getEmail(),
                userEntity.getRole().name(),
                userEntity.getProfileImage(),
                userEntity.getBannerImage(),
                userEntity.isActive(),
                String.valueOf(userEntity.getTotalScreenTime()),
                userEntity.getPoints(),
                userEntity.getLevel(),
                userEntity.getMilestones()
        );
    }

    public static UserEntity toUserEntity(User user) {
        return new UserEntity(
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getProfileImage(),
                user.getBannerImage(),
                true,
                user.getRole()
        );
    }
}
