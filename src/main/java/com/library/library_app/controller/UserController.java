package com.library.library_app.controller;

import com.library.library_app.dto.model.StudentDto;
import com.library.library_app.dto.model.UserDto;
import com.library.library_app.dto.request.LoginRequest;
import com.library.library_app.dto.request.NewStudentRequest;
import com.library.library_app.dto.request.RegisterRequest;
import com.library.library_app.dto.response.LoginResponse;
import com.library.library_app.dto.response.RegisterResponse;
import com.library.library_app.entity.UserEntity;
import com.library.library_app.service.LoginService;
import com.library.library_app.service.StudentService;
import com.library.library_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;
    private final LoginService loginService;
    private final StudentService studentService;

    @PostMapping("/register/admin")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<RegisterResponse> registerAdmin(@RequestBody RegisterRequest request,Integer studentNumber) {
        userService.registerAdmin(request, studentNumber);
        return ResponseEntity.ok(new RegisterResponse());
    }

    @PostMapping("/register/student")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<RegisterResponse> registerStudent(@RequestBody NewStudentRequest request) {
        studentService.registerNewStudent(request);
        return ResponseEntity.ok(new RegisterResponse());
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        String token = loginService.login(loginRequest);
        return ResponseEntity.ok(new LoginResponse(token)); // JWT token döndürülüyor
    }

    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getAllUser() {
        return userService.getAllUser();
    }

    @GetMapping("/student")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentDto> getAllStudent(){
        return studentService.getAllStudent();
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable String userId){
        userService.deleteUserById(userId);
        return ResponseEntity.noContent().build();
    }
}
