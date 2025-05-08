package com.library.library_app.service;

import com.library.library_app.dto.model.UserDto;
import com.library.library_app.dto.request.RegisterRequest;
import com.library.library_app.entity.UserEntity;
import com.library.library_app.exception.CustomIllegalArgumentException;
import com.library.library_app.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private EmailValidationService emailValidationService;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<UserEntity> userEntityCaptor;

    @Test
    void registerUser_shouldSaveUser_whenUserDoesNotExistAndEmailIsValid(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("salih");
        registerRequest.setPassword("123");
        registerRequest.setEmail("salih@gmail");
        registerRequest.setRole("USER");

        when(userRepository.findByUsername("salih")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("123")).thenReturn("encodedPassword");

        userService.registerUser(registerRequest, 123456);

        verify(emailValidationService).validateStudentEmail(123456,"salih@gmail");
        verify(userRepository).save(userEntityCaptor.capture());

        UserEntity savedUser = userEntityCaptor.getValue();
        assertEquals("salih", savedUser.getUsername());
        assertEquals("encodedPassword", savedUser.getPassword());
        assertEquals("salih@gmail", savedUser.getEmail());
        assertEquals("USER", savedUser.getRole());
    }

    @Test
    void registerUser_shouldThrowException_whenUsernameAlreadyExists() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("existingUser");

        when(userRepository.findByUsername("existingUser")).thenReturn(Optional.of(new UserEntity()));

        assertThrows(CustomIllegalArgumentException.class, () -> {
            userService.registerUser(registerRequest, 123);
        });
    }

    @Test
    void registerUser_shouldSkipEmailValidation_whenStudentNumberIsNull() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("admin");
        registerRequest.setPassword("123");
        registerRequest.setEmail("admin@gmail");
        registerRequest.setRole("ADMIN");

        when(userRepository.findByUsername("admin")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("123")).thenReturn("encodedAdmin");

        userService.registerUser(registerRequest, null);

        verify(emailValidationService, never()).validateStudentEmail(any(), any());
        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void registerAdmin_shouldSetRoleAsAdminAndCallRegisterUser() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("admin");
        registerRequest.setPassword("123");
        registerRequest.setEmail("admin@gmail");

        when(userRepository.findByUsername("admin")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("123")).thenReturn("encodedAdmin");

        userService.registerAdmin(registerRequest, 123456);

        verify(userRepository).save(userEntityCaptor.capture());
        assertEquals("ADMIN", userEntityCaptor.getValue().getRole());
    }

    @Test
    void  getAllUser_shouldReturnMappedUserDtos() {
        UserEntity user1 = UserEntity.builder()
                .id("1")
                .username("user1")
                .email("u1@mail.com")
                .role("USER")
                .build();

        UserEntity user2 = UserEntity.builder()
                .id("2")
                .username("user2")
                .email("u2@mail.com")
                .role("ADMIN")
                .build();

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<UserDto> userDtos = userService.getAllUser();

        assertEquals(2, userDtos.size());
        assertEquals("user1", userDtos.get(0).getUsername());
        assertEquals("ADMIN", userDtos.get(1).getRole());
    }

    @Test
    void deleteUserById_shouldDeleteUser_whenUserExists() {
        UserEntity user = new UserEntity();
        user.setId("abc123");

        when(userRepository.findById("abc123")).thenReturn(Optional.of(user));

        userService.deleteUserById("abc123");

        verify(userRepository).delete(user);
    }

    @Test
    void deleteUserById_shouldThrowException_whenUserNotFound() {
        when(userRepository.findById("notFoundId")).thenReturn(Optional.empty());

        assertThrows(CustomIllegalArgumentException.class, () ->
                userService.deleteUserById("notFoundId"));
    }

}