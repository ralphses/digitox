package com.clicks.digitox.application.user.ports.api;

import com.clicks.digitox.application.user.payloads.LoginRequest;
import com.clicks.digitox.application.user.payloads.RegisterUserRequest;
import com.clicks.digitox.shared.utils.ApiResponse;

public interface UserApi {

    ApiResponse register(RegisterUserRequest registerUserRequest);
    ApiResponse login(LoginRequest loginRequest);
    ApiResponse profile(String email);

}
