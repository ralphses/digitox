package com.clicks.digitox.domain.user.service;

import com.clicks.digitox.domain.sleep_info.utils.SleepInfoMapper;
import com.clicks.digitox.domain.user.dto.UserDto;
import com.clicks.digitox.domain.user.entity.User;
import com.clicks.digitox.domain.user.entity.UserRole;
import com.clicks.digitox.domain.user.repository.UserRepository;
import com.clicks.digitox.shared.annotations.ApplicationService;
import com.clicks.digitox.shared.utils.ApplicationResourceService;

import static com.clicks.digitox.domain.user.utils.DomainUserMapper.userDto;


@ApplicationService
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ApplicationResourceService applicationResourceService;

    public UserServiceImpl(UserRepository userRepository,
                           ApplicationResourceService applicationResourceService) {
        this.userRepository = userRepository;
        this.applicationResourceService = applicationResourceService;
    }

    @Override
    public UserDto createNewUser(String name, String email, String encodedPassword) {
        User user = new User(
                name,
                email,
                encodedPassword,
                applicationResourceService.getDefaultProfileImage(),
                applicationResourceService.getDefaultBannerImage(),
                UserRole.USER);
        return userRepository.save(user);
    }

    @Override
    public UserDto findUserByEmail(String email) {
        User user = userRepository.findByUsername(email);
        return userDto(user);
    }

    @Override
    public boolean addMilestoneToUser(String milestoneId, String userEmail) {

        User user = userRepository.findByUsername(userEmail);
        user.getMilestones().add(milestoneId);
        userRepository.update(user);
        return true;
    }

    @Override
    public boolean exists(String userEmail) {
        return userRepository.exists(userEmail);
    }

    @Override
    public String updateTotalScreenTime(String userEmail, long currentScreenTime) {
        long updatedScreenTime = userRepository.addScreenTIme(userEmail, currentScreenTime);
        System.out.println("updatedScreenTime = " + updatedScreenTime);
        return SleepInfoMapper.formatTime(updatedScreenTime);
    }
}
