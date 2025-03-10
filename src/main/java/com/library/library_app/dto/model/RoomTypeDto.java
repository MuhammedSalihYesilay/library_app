package com.library.library_app.dto.model;

import com.library.library_app.entity.RoomTypeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomTypeDto {

    private String id;
    private String title;
    private int capacity;

    public static RoomTypeDto from(RoomTypeEntity roomType) {
        return RoomTypeDto.builder()
                .id(roomType.getId())
                .title(roomType.getTitle())
                .capacity(roomType.getCapacity())
                .build();
    }
}
