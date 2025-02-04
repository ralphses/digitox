package com.clicks.digitox.application.user.application;

import com.clicks.digitox.application.user.payloads.RegisterUserRequest;
import com.clicks.digitox.application.user.payloads.RegisterUserResponse;
import com.clicks.digitox.application.user.ports.spi.UserSecurityService;
import com.clicks.digitox.application.user.use_case.RegisterUser;
import com.clicks.digitox.domain.sleep_info.dto.SleepInfoDto;
import com.clicks.digitox.domain.sleep_info.service.SleepInfoService;
import com.clicks.digitox.domain.user.dto.UserDto;
import com.clicks.digitox.domain.user.entity.UserLevel;
import com.clicks.digitox.domain.user.entity.UserRole;
import com.clicks.digitox.domain.user.exceptions.UserAlreadyExistException;
import com.clicks.digitox.domain.user.repository.UserInMemoryRepository;
import com.clicks.digitox.domain.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterUserTest {

    @Mock
    private UserService userService;

    @Mock
    private UserSecurityService userSecurityService;

    @Mock
    private UserInMemoryRepository userInMemoryRepository;

    @Mock
    private SleepInfoService sleepInfoService;

    @InjectMocks
    private RegisterUser registerUser;


    @Test
    void execute_ShouldRegisterUserSuccessfully() {
        // Arrange
//        RegisterUserRequest request = new RegisterUserRequest("John Doe", "johndoe@example.com", "password123");
//        String encodedPassword = "encodedPassword123";
//        String profileImage = "https://via.placeholder.com/150";
//        String bannerImage = "https://via.placeholder.com/400x200";
//
//        UserDto expectedUser = new UserDto(
//                "Name",
//                "email@gmail.com",
//                UserRole.USER.name(),
//                profileImage,
//                bannerImage,
//                true,
//                20L,
//                20L,
//                UserLevel.BEGINNER,
//                new ArrayList<>());
//        String accessToken = "accessToken123";
//        SleepInfoDto sleepInfo = new SleepInfoDto("2", "2", "2%");
//
//        when(userInMemoryRepository.userIdExists(request.email())).thenReturn(false);
//        when(userSecurityService.getNewAccessToken(expectedUser.email(), expectedUser.role())).thenReturn(accessToken);
//        when(userSecurityService.encodePassword(request.password())).thenReturn(encodedPassword);
//        when(userService.createNewUser(request.name(), request.email(), encodedPassword)).thenReturn(expectedUser);
//        when(sleepInfoService.createForUser(request.email())).thenReturn(sleepInfo);
//        // Act
//        RegisterUserResponse registerUserResponse = registerUser.execute(request);
//
//        // Assert
//        assertNotNull(registerUserResponse);
//        assertNotNull(registerUserResponse.token());
//        assertNotNull(registerUserResponse.user());
//        assertEquals(expectedUser, registerUserResponse.user());
//        verify(userInMemoryRepository, times(1)).userIdExists(request.email());
//        verify(userSecurityService, times(1)).encodePassword(request.password());
//        verify(userInMemoryRepository, times(1)).addUserId(request.email());
//        verify(userService, times(1)).createNewUser(request.name(), request.email(), encodedPassword);
    }

    @Test
    void execute_ShouldThrowExceptionWhenUserAlreadyExists() {
        // Arrange
        RegisterUserRequest request = new RegisterUserRequest("John Doe", "johndoe@example.com", "password123");

        when(userInMemoryRepository.userIdExists(request.email())).thenReturn(true);

        // Act & Assert
        UserAlreadyExistException exception = assertThrows(UserAlreadyExistException.class, () -> registerUser.execute(request));
        assertEquals("User with email address johndoe@example.com already exists", exception.getMessage());
        verify(userInMemoryRepository, times(1)).userIdExists(request.email());
        verifyNoInteractions(userSecurityService);
        verifyNoInteractions(userService);
    }

    @Test
    void execute_ShouldNotCallServiceWhenPasswordEncodingFails() {
        // Arrange
        RegisterUserRequest request = new RegisterUserRequest("John Doe", "johndoe@example.com", "password123");

        when(userInMemoryRepository.userIdExists(request.email())).thenReturn(false);
        when(userSecurityService.encodePassword(request.password())).thenThrow(new RuntimeException("Password encoding failed"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> registerUser.execute(request));
        assertEquals("Password encoding failed", exception.getMessage());
        verify(userInMemoryRepository, times(1)).userIdExists(request.email());
        verify(userSecurityService, times(1)).encodePassword(request.password());
        verifyNoInteractions(userService);
    }
}
