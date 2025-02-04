package com.clicks.digitox.application.user.use_case;

import com.clicks.digitox.application.user.payloads.RegisterUserRequest;
import com.clicks.digitox.application.user.payloads.RegisterUserResponse;
import com.clicks.digitox.application.user.ports.spi.UserSecurityService;
import com.clicks.digitox.domain.sleep_info.dto.SleepInfoDto;
import com.clicks.digitox.domain.sleep_info.service.SleepInfoService;
import com.clicks.digitox.domain.user.dto.UserDto;
import com.clicks.digitox.domain.user.exceptions.UserAlreadyExistException;
import com.clicks.digitox.domain.user.repository.UserInMemoryRepository;
import com.clicks.digitox.domain.user.service.UserService;
import com.clicks.digitox.shared.annotations.UseCase;

@UseCase
public class RegisterUser {

    private final UserService userService;
    private final UserSecurityService userSecurityService;
    private final UserInMemoryRepository userInMemoryRepository;
    private final SleepInfoService sleepInfoService;

    public RegisterUser(UserService userService, UserSecurityService userSecurityService, UserInMemoryRepository userInMemoryRepository, SleepInfoService sleepInfoService) {
        this.userService = userService;
        this.userSecurityService = userSecurityService;
        this.userInMemoryRepository = userInMemoryRepository;
        this.sleepInfoService = sleepInfoService;
    }

    public RegisterUserResponse execute(RegisterUserRequest registerUserRequest) {

        // Check if user exists with email
        validateRegisterRequest(registerUserRequest);

        // Hash user password
        String encodedPassword = userSecurityService.encodePassword(registerUserRequest.password());

        // Persist new user
        UserDto newUser = persistUser(registerUserRequest, encodedPassword);

        // Get access token
        String accessToken = userSecurityService.getNewAccessToken(newUser.email(), newUser.role());

        // Create a new sleep information record
        SleepInfoDto stats = sleepInfoService.createForUser(newUser.email());

        return new RegisterUserResponse(newUser, stats, accessToken);
    }

    private UserDto persistUser(RegisterUserRequest registerUserRequest, String encodedPassword) {

        // Save User entity
        UserDto newUser = userService.createNewUser(registerUserRequest.name(), registerUserRequest.email(), encodedPassword);

        // Persist user ID in memory
        userInMemoryRepository.addUserId(registerUserRequest.email());
        return newUser;
    }

    private void validateRegisterRequest(RegisterUserRequest registerUserRequest) {
        if (userInMemoryRepository.userIdExists(registerUserRequest.email())) {
            throw new UserAlreadyExistException(registerUserRequest.email());
        }
    }
}
