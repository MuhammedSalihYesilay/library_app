package com.library.library_app.service;

import com.library.library_app.dto.model.StudentDto;
import com.library.library_app.dto.request.NewStudentRequest;
import com.library.library_app.entity.StudentEntity;
import com.library.library_app.entity.UserEntity;
import com.library.library_app.exception.CustomIllegalArgumentException;
import com.library.library_app.exception.StudentIllegalArgumentException;
import com.library.library_app.repository.StudentRepository;
import com.library.library_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final StudentRecordsService studentRecordsService;

    public void registerNewStudent(NewStudentRequest newStudentRequest) {
        validateStudentNumber(newStudentRequest.getStudentNumber());
        newStudentRequest.getRegisterRequest().setRole("STUDENT");
        userService.registerUser(newStudentRequest.getRegisterRequest(), newStudentRequest.getStudentNumber());

        // Kaydedilen kullanıcıyı bul
        UserEntity userEntity = userRepository.findByUsername(newStudentRequest.getRegisterRequest().getUsername())
                .orElseThrow(() -> new CustomIllegalArgumentException(newStudentRequest.getRegisterRequest().getUsername()));

        saveStudent(newStudentRequest, userEntity);
    }

    // Öğrenci numarasının kayıtlı öğrenciler listesinde olup olmadığını kontrol et
    private void validateStudentNumber(Integer studentNumber) {
        if (!isValidStudentNumber(studentNumber)) {
            throw new StudentIllegalArgumentException(studentNumber);
        }
    }

    private boolean isValidStudentNumber(Integer studentNumber) {
        return studentRecordsService.isValidStudentNumber(studentNumber);
    }

    // Öğrenciyi kaydet
    private void saveStudent(NewStudentRequest newStudentRequest, UserEntity userEntity) {
        StudentEntity studentEntity = StudentEntity.builder()
                .fullName(newStudentRequest.getFullName())
                .studentNumber(newStudentRequest.getStudentNumber())
                .user(userEntity)
                .build();

        userEntity.setStudent(studentEntity);
        studentRepository.save(studentEntity);
    }

    public List<StudentDto> getAllStudent() {
       return studentRepository.findAll()
               .stream()
               .map(StudentEntity -> StudentDto.builder()
                       .id(StudentEntity.getId())
                       .fullName(StudentEntity.getFullName())
                       .studentNumber(StudentEntity.getStudentNumber())
                          .build())
                .collect(Collectors.toList());
    }
}
