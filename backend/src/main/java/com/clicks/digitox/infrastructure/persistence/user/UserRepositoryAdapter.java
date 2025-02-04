package com.clicks.digitox.infrastructure.persistence.user;

import com.clicks.digitox.domain.sleep_info.exceptions.SleepInfoExceptionNotFound;
import com.clicks.digitox.domain.user.dto.UserDto;
import com.clicks.digitox.domain.user.entity.User;
import com.clicks.digitox.domain.user.exceptions.UserNotFoundException;
import com.clicks.digitox.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;


@Component
public class UserRepositoryAdapter implements UserRepository {
    private final JpaUserRepository jpaUserRepository;

    public UserRepositoryAdapter(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    @Transactional
    public User findByUsername(String username) {
        User user = jpaUserRepository.findByEmail(username)
                .map(UserMapper::toUser)
                .orElseThrow(UserNotFoundException::new);
        System.out.println("user = " + user.getMilestones());
        return user;
    }

    @Override
    public UserDto save(User user) {
        UserEntity userEntity = jpaUserRepository.save(UserMapper.toUserEntity(user));
        return UserMapper.toUserDto(userEntity);
    }

    @Override
    public boolean exists(String userEmail) {
        return jpaUserRepository.existsByEmail(userEmail);
    }

    @Override
    @Transactional
    public void update(User user) {
        jpaUserRepository.findByEmail(user.getEmail()).ifPresent(userEntity -> {
            userEntity.getMilestones().addAll(user.getMilestones());
        });
    }

    @Override
    @Transactional
    public long addScreenTIme(String userEmail, long currentScreenTime) {
        UserEntity userEntity = jpaUserRepository.findByEmail(userEmail).orElseThrow(SleepInfoExceptionNotFound::new);
        userEntity.addScreenTime(currentScreenTime);
        return userEntity.getTotalScreenTime();
    }
}
