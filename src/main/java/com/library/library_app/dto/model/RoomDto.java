package com.library.library_app.dto.model;


import com.library.library_app.entity.RoomEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {

    private String id;
    private int roomNumber;
    private LocalDateTime createDate;
    private RoomTypeDto roomType;

    public static RoomDto from(RoomEntity room) {
        return RoomDto.builder()
                .id(room.getId())
                .roomNumber(room.getRoomNumber())
                .createDate(room.getCreateDate())
                .roomType(RoomTypeDto.from(room.getRoomType()))
                .build();
    }
}
