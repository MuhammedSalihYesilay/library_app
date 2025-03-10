package com.library.library_app.service;

import com.library.library_app.dto.model.RoomDto;
import com.library.library_app.dto.request.NewRoomRequest;
import com.library.library_app.entity.RoomEntity;
import com.library.library_app.entity.RoomTypeEntity;
import com.library.library_app.exception.RoomNotFoundException;
import com.library.library_app.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomTypeService roomTypeService;
    private final RoomRepository roomRepository;

    public RoomDto createRoom(NewRoomRequest newRoomRequest) {
        RoomEntity roomEntity = buildRoomEntity(newRoomRequest);
        RoomEntity savedRoom = roomRepository.save(roomEntity);

        return RoomDto.from(savedRoom);
    }

    private RoomEntity buildRoomEntity(NewRoomRequest newRoomRequest) {
        RoomTypeEntity roomTypeEntity = roomTypeService.validateAndGetRoomType(newRoomRequest.getRoomTypeId());

        return RoomEntity.builder()
                .roomNumber(newRoomRequest.getRoomNumber())
                .roomType(roomTypeEntity)
                .build();
    }

    @Transactional(readOnly = true)
    public List<RoomDto> getAllRooms() {
        return roomRepository.findAll()
                .stream()
                .map(RoomDto::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public RoomDto getRoomById(String roomId) {
        RoomEntity roomEntity = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException(roomId));

        return RoomDto.from(roomEntity);
    }

    @Transactional
    public RoomDto updateRoom(String roomId, NewRoomRequest updateRoomRequest) {
        RoomEntity roomEntity = validateAndGetRoom(roomId);
        RoomTypeEntity roomTypeEntity = roomTypeService.validateAndGetRoomType(updateRoomRequest.getRoomTypeId());

        roomEntity.setRoomNumber(updateRoomRequest.getRoomNumber());
        roomEntity.setRoomType(roomTypeEntity);

        return RoomDto.from(roomRepository.save(roomEntity));
    }

    private RoomEntity validateAndGetRoom(String roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException(roomId));
    }

    @Transactional
    public void deleteRoom(String roomId) {
        RoomEntity roomEntity = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException(roomId));

        roomRepository.delete(roomEntity);
    }
}
