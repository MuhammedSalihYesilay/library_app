package com.library.library_app.dto.model;


import com.library.library_app.entity.ReservationEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto {

    private String id;
    private int periodNumber;
    private LocalDate date;
    private LocalDateTime createDate;
    private RoomDto room;
    private Set<StudentDto> students;

    public static ReservationDto from(ReservationEntity reservation) {
        return ReservationDto.builder()
                .id(reservation.getId())
                .periodNumber(reservation.getPeriodNumber())
                .date(reservation.getDate())
                .createDate(reservation.getCreateDate())
                .room(RoomDto.from(reservation.getRoom()))
                .students(reservation.getStudents().stream().map(StudentDto::from).collect(Collectors.toSet()))
                .build();
    }
}
