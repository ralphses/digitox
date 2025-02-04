package com.clicks.digitox.infrastructure.api;

import com.clicks.digitox.application.user.ports.api.UserApi;
import com.clicks.digitox.application.user.use_case.GetUserProfile;
import com.clicks.digitox.application.user.use_case.LoginUser;
import com.clicks.digitox.application.user.use_case.RegisterUser;
import com.clicks.digitox.application.user.payloads.LoginRequest;
import com.clicks.digitox.application.user.payloads.LoginResponse;
import com.clicks.digitox.application.user.payloads.RegisterUserRequest;
import com.clicks.digitox.application.user.payloads.RegisterUserResponse;
import com.clicks.digitox.shared.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("api/v1/auth")
public class UserController implements UserApi {

    private final RegisterUser registerUser;
    private final LoginUser loginUser;
    private final GetUserProfile getUserProfile;

    public UserController(RegisterUser registerUser, LoginUser loginUser, GetUserProfile getUserProfile) {
        this.registerUser = registerUser;
        this.loginUser = loginUser;
        this.getUserProfile = getUserProfile;
    }

    @Override
    @ResponseStatus(CREATED)
    @PostMapping("register")
    public ApiResponse register(@Valid @RequestBody RegisterUserRequest registerUserRequest) {
        RegisterUserResponse savedUser = registerUser.execute(registerUserRequest);
        return new ApiResponse(true, savedUser);
    }

    @Override
    @PostMapping("login")
    public ApiResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse loggedInUser = loginUser.execute(loginRequest);
        return new ApiResponse(true, loggedInUser);
    }

    @Override
    @GetMapping("profile/{email}")
    public ApiResponse profile(@PathVariable String email) {
        return new ApiResponse(true, getUserProfile.execute(email));
    }
}
