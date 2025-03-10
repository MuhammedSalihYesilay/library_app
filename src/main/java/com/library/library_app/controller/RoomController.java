package com.library.library_app.controller;

import com.library.library_app.dto.model.RoomDto;
import com.library.library_app.dto.request.NewRoomRequest;
import com.library.library_app.service.RoomService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoomDto createRoom(@RequestBody NewRoomRequest newRoomRequest,
                              UriComponentsBuilder ucb,
                              HttpServletResponse response
    ) {
        RoomDto roomDto = roomService.createRoom(newRoomRequest);

        URI locationOfNewRoom = ucb
                .path("/api/rooms/{id}")
                .buildAndExpand(roomDto.getId())
                .toUri();

        response.setHeader("Location", locationOfNewRoom.toString());
        return roomDto;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<RoomDto> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/{roomId}")
    @ResponseStatus(HttpStatus.OK)
    public RoomDto getRoomById(@PathVariable String roomId) {
        return roomService.getRoomById(roomId);
    }

    @PutMapping("/{roomId}")
    @ResponseStatus(HttpStatus.OK)
    public RoomDto updateRoom(@PathVariable String roomId, @RequestBody NewRoomRequest updateRoomRequest){
        return roomService.updateRoom(roomId, updateRoomRequest);
    }

    @DeleteMapping("/{roomId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoomById(@PathVariable String roomId) {
        roomService.deleteRoom(roomId);
    }
}