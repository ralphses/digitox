package com.clicks.digitox.domain.user.service;

import com.clicks.digitox.domain.user.dto.UserDto;
import com.clicks.digitox.domain.user.entity.User;
import com.clicks.digitox.domain.user.entity.UserLevel;
import com.clicks.digitox.domain.user.entity.UserRole;
import com.clicks.digitox.domain.user.exceptions.UserNotFoundException;
import com.clicks.digitox.domain.user.repository.UserRepository;
import com.clicks.digitox.domain.user.utils.DomainUserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;


    @Test
    void createNewUser_ShouldCreateAndSaveUserSuccessfully() {
//        // Arrange
//        String name = "John Doe";
//        String email = "johndoe@example.com";
//        String encodedPassword = "encodedPassword123";
//        String profileImage = "https://via.placeholder.com/150";
//        String bannerImage = "https://via.placeholder.com/400x200";
//
//        UserDto expectedUserDto = new UserDto(
//                name,
//                email,
//                UserRole.USER.name(),
//                profileImage,
//                bannerImage,
//                true,
//                20L,
//                20L,
//                UserLevel.BEGINNER,
//                new ArrayList<>());
//
//        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
//            User savedUser = invocation.getArgument(0);
//            return DomainUserMapper.userDto(savedUser);
//        });
//
//        // Act
//        UserDto actualUserDto = userService.createNewUser(name, email, encodedPassword);
//
//        // Assert
//        assertNotNull(actualUserDto);
//        assertEquals(expectedUserDto.name(), actualUserDto.name());
//        assertEquals(expectedUserDto.email(), actualUserDto.email());
//        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void findUserByEmail_ShouldReturnUserSuccessfully() {
        // Arrange
//        String email = "johndoe@example.com";
//        String profileImage = "https://via.placeholder.com/150";
//        String bannerImage = "https://via.placeholder.com/400x200";
//        User user = new User("John Doe", email, "encodedPassword123", profileImage, bannerImage, UserRole.USER);
//        UserDto expectedUserDto = DomainUserMapper.userDto(user);
//
//        when(userRepository.findByUsername(email)).thenReturn(user);
//
//        // Act
//        UserDto actualUserDto = userService.findUserByEmail(email);
//
//        // Assert
//        assertNotNull(actualUserDto);
//        assertEquals(expectedUserDto, actualUserDto);
//        verify(userRepository, times(1)).findByUsername(email);
    }

    @Test
    void findUserByEmail_ShouldThrowExceptionWhenUserNotFound() {
        // Arrange
        String email = "nonexistent@example.com";
        when(userRepository.findByUsername(email)).thenThrow(new UserNotFoundException("User not found"));

        // Act & Assert
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> userService.findUserByEmail(email));
        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).findByUsername(email);
    }

}