package com.library.library_app.service;

import com.library.library_app.dto.model.UserDto;
import com.library.library_app.dto.request.RegisterRequest;
import com.library.library_app.entity.UserEntity;
import com.library.library_app.exception.CustomIllegalArgumentException;
import com.library.library_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailValidationService emailValidationService;

    public void registerUser(RegisterRequest registerRequest, Integer studentNumber) {
        ensureUserDoesNotExist(registerRequest.getUsername());
        validateEmail(studentNumber, registerRequest.getEmail());
        saveUser(registerRequest);
    }

    private void ensureUserDoesNotExist(String username) {
        userRepository.findByUsername(username)
                .ifPresent(user -> {
                    throw new CustomIllegalArgumentException(username);
                });
    }

    private void validateEmail(Integer studentNumber, String email) {
        if (studentNumber == null) {
            // Admin için email doğrulaması yok
            return;
        }

        emailValidationService.validateStudentEmail(studentNumber, email);
    }

    private void saveUser(RegisterRequest registerRequest) {
        UserEntity userEntity = UserEntity.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .email(registerRequest.getEmail())
                .role(registerRequest.getRole())
                .build();
        userRepository.save(userEntity);
    }

    public void registerAdmin(RegisterRequest registerRequest, Integer studentNumber) {

        // Role'ü ADMIN olarak ayarla
        registerRequest.setRole("ADMIN");
        registerUser(registerRequest, studentNumber);
    }

    public List<UserDto> getAllUser() {
        return userRepository.findAll()
                .stream()
                .map(userEntity -> UserDto.builder()
                        .id(userEntity.getId())
                        .username(userEntity.getUsername())
                        .email(userEntity.getEmail())
                        .role(userEntity.getRole())
                        .build())
                .collect(java.util.stream.Collectors.toList());
    }

    public void deleteUserById(String userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new CustomIllegalArgumentException(userId));
        userRepository.delete(userEntity);
    }
}
