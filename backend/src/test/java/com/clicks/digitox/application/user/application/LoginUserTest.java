package com.clicks.digitox.application.user.application;

import com.clicks.digitox.application.user.payloads.LoginRequest;
import com.clicks.digitox.application.user.payloads.LoginResponse;
import com.clicks.digitox.application.user.ports.spi.UserSecurityService;
import com.clicks.digitox.application.user.use_case.LoginUser;
import com.clicks.digitox.domain.sleep_info.dto.SleepInfoDto;
import com.clicks.digitox.domain.sleep_info.service.SleepInfoService;
import com.clicks.digitox.domain.user.dto.UserDto;
import com.clicks.digitox.domain.user.entity.UserLevel;
import com.clicks.digitox.domain.user.entity.UserRole;
import com.clicks.digitox.domain.user.repository.UserInMemoryRepository;
import com.clicks.digitox.domain.user.service.UserService;
import com.clicks.digitox.domain.user.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static com.clicks.digitox.domain.user.entity.UserRole.USER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginUserTest {

    @Mock
    private UserService userService;

    @Mock
    private UserSecurityService userSecurityService;

    @Mock
    private SleepInfoService sleepInfoService;

    private LoginUser loginUser;

    @BeforeEach
    void setUp() {
        loginUser = new LoginUser(userService, userSecurityService, mock(UserInMemoryRepository.class), sleepInfoService);
    }

    @Test
    void execute_ShouldReturnLoginResponse_WhenCredentialsAreValid() {
        // Arrange
        String email = "valid@example.com";
        String password = "password123";
        String token = "access_token_123";
        String profileImage = "https://via.placeholder.com/150";
        String bannerImage = "https://via.placeholder.com/400x200";
        SleepInfoDto sleepInfo = new SleepInfoDto("2h", "2h", "3%");

        UserDto userDto = new UserDto(
                "Name",
                "email@gmail.com",
                UserRole.USER.name(),
                profileImage,
                bannerImage,
                true,
                "20L",
                20L,
                UserLevel.BEGINNER,
                new ArrayList<>());

        when(userSecurityService.attemptLogin(email, password)).thenReturn(token);
        when(userService.findUserByEmail(email)).thenReturn(userDto);
        when(sleepInfoService.getForUser(email)).thenReturn(sleepInfo);

        LoginRequest loginRequest = new LoginRequest(email, password);

        // Act
        LoginResponse response = loginUser.execute(loginRequest);

        // Assert
        assertNotNull(response);
        assertEquals(userDto, response.user());
        assertEquals(token, response.token());
        verify(userSecurityService, times(1)).attemptLogin(email, password);
        verify(userService, times(1)).findUserByEmail(email);
    }

    @Test
    void execute_ShouldThrowException_WhenCredentialsAreInvalid() {
        // Arrange
        String email = "invalid@example.com";
        String password = "wrongPassword";

        when(userSecurityService.attemptLogin(email, password))
                .thenThrow(new IllegalArgumentException("Invalid credentials"));

        LoginRequest loginRequest = new LoginRequest(email, password);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> loginUser.execute(loginRequest));
        assertEquals("Invalid credentials", exception.getMessage());
        verify(userSecurityService, times(1)).attemptLogin(email, password);
        verify(userService, never()).findUserByEmail(email);
    }

    @Test
    void execute_ShouldThrowException_WhenUserNotFound() {
        // Arrange
        String email = "nonexistent@example.com";
        String password = "password123";
        String token = "access_token_123";

        when(userSecurityService.attemptLogin(email, password)).thenReturn(token);
        when(userService.findUserByEmail(email)).thenThrow(new UserNotFoundException("User not found"));

        LoginRequest loginRequest = new LoginRequest(email, password);

        // Act & Assert
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> loginUser.execute(loginRequest));
        assertEquals("User not found", exception.getMessage());
        verify(userSecurityService, times(1)).attemptLogin(email, password);
        verify(userService, times(1)).findUserByEmail(email);
    }
}