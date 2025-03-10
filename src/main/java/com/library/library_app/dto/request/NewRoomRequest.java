package com.library.library_app.dto.request;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class NewRoomRequest {

    private int roomNumber;
    private String roomTypeId;
}
