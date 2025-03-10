package com.library.library_app.controller;

import com.library.library_app.dto.model.RoomTypeDto;
import com.library.library_app.dto.request.NewRoomTypeRequest;
import com.library.library_app.service.RoomTypeService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/room_types")
public class RoomTypeController {

    private final RoomTypeService roomTypeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoomTypeDto createRoomType(@RequestBody NewRoomTypeRequest newRoomTypeRequest,
                              UriComponentsBuilder ucb,
                              HttpServletResponse response
    ) {
        RoomTypeDto roomTypeDto = roomTypeService.createRoomType(newRoomTypeRequest);

        URI locationOfNewRoom = ucb
                .path("/api/rooms_types/{id}")
                .buildAndExpand(roomTypeDto.getId())
                .toUri();

        response.setHeader("Location", locationOfNewRoom.toString());
        return roomTypeDto;
    }
    @GetMapping("/{roomTypeId}")
    @ResponseStatus(HttpStatus.OK)
    public RoomTypeDto getRoomTypeById(@PathVariable String roomTypeId){
        return roomTypeService.getRoomTypeById(roomTypeId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<RoomTypeDto> getAllRoomTypes() {
        return roomTypeService.getAllRoomTypes();
    }

    @PutMapping("/{roomTypeId}")
    @ResponseStatus(HttpStatus.OK)
    public RoomTypeDto updateRoomType(@PathVariable String roomTypeId,
                                      @RequestBody NewRoomTypeRequest newRoomTypeRequest
    ) {
        return roomTypeService.updateRoomType(roomTypeId, newRoomTypeRequest);
    }

    @DeleteMapping("/{roomTypeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoomType(@PathVariable String roomTypeId) {
        roomTypeService.deleteRoomType(roomTypeId);
    }
}
