package com.clicks.digitox.application.user.use_case;

import com.clicks.digitox.application.user.payloads.LoginRequest;
import com.clicks.digitox.application.user.payloads.LoginResponse;
import com.clicks.digitox.application.user.ports.spi.UserSecurityService;
import com.clicks.digitox.domain.sleep_info.dto.SleepInfoDto;
import com.clicks.digitox.domain.sleep_info.service.SleepInfoService;
import com.clicks.digitox.domain.user.dto.UserDto;
import com.clicks.digitox.domain.user.repository.UserInMemoryRepository;
import com.clicks.digitox.domain.user.service.UserService;
import com.clicks.digitox.shared.annotations.UseCase;

@UseCase
public class LoginUser {

    private final UserService userService;
    private final UserSecurityService userSecurityService;
    private final SleepInfoService sleepInfoService;

    public LoginUser(UserService userService, UserSecurityService userSecurityService, UserInMemoryRepository userInMemoryRepository, SleepInfoService sleepInfoService) {
        this.userService = userService;
        this.userSecurityService = userSecurityService;
        this.sleepInfoService = sleepInfoService;
    }

    public LoginResponse execute(LoginRequest loginRequest) {

        String userEmail = loginRequest.email();

        // Attempt Logging in
        String authenticatedUserAccessToken = userSecurityService.attemptLogin(userEmail, loginRequest.password());

        // Get User profile
        UserDto user = userService.findUserByEmail(userEmail);

        // Get user sleep info
        SleepInfoDto stats = sleepInfoService.getForUser(userEmail);

        // Return a login response
        return new LoginResponse(user, stats, authenticatedUserAccessToken);
    }
}
