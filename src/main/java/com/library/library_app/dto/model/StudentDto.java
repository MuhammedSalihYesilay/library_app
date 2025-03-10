package com.library.library_app.dto.model;

import com.library.library_app.entity.StudentEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {

    private String id;
    private String fullName;
    private int studentNumber;
    private UserDto user;

    public static StudentDto from(StudentEntity student) {
        return StudentDto.builder()
                .id(student.getId())
                .fullName(student.getFullName())
                .studentNumber(student.getStudentNumber())
                .user(UserDto.from(student.getUser()))
                .build();
    }
}
