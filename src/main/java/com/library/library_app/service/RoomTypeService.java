package com.library.library_app.service;

import com.library.library_app.dto.model.RoomTypeDto;
import com.library.library_app.dto.request.NewRoomTypeRequest;
import com.library.library_app.entity.RoomTypeEntity;
import com.library.library_app.exception.RoomTypeNotFoundException;
import com.library.library_app.repository.RoomTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomTypeService {

    private final RoomTypeRepository roomTypeRepository;

    public RoomTypeDto createRoomType(NewRoomTypeRequest newRoomTypeRequest) {
        RoomTypeEntity roomTypeEntity = buildRoomTypeEntity(newRoomTypeRequest);
        RoomTypeEntity savedRoomType = roomTypeRepository.save(roomTypeEntity);

        return RoomTypeDto.from(savedRoomType);
    }

    private RoomTypeEntity buildRoomTypeEntity(NewRoomTypeRequest newRoomTypeRequest) {
        return RoomTypeEntity.builder()
                .title(newRoomTypeRequest.getTitle())
                .capacity(newRoomTypeRequest.getCapacity())
                .build();
    }

    public RoomTypeEntity validateAndGetRoomType(String roomTypeId) {
        return roomTypeRepository.findById(roomTypeId)
                .orElseThrow(() -> new RoomTypeNotFoundException(roomTypeId));
    }

    public RoomTypeDto getRoomTypeById(String roomTypeId) {
        RoomTypeEntity roomTypeEntity = roomTypeRepository.findById(roomTypeId)
                .orElseThrow(() -> new RoomTypeNotFoundException(roomTypeId));

        return RoomTypeDto.from(roomTypeEntity);
    }

    public List<RoomTypeDto> getAllRoomTypes() {
        return roomTypeRepository.findAll()
                .stream()
                .map(RoomTypeDto::from)
                .collect(Collectors.toList());
    }

    public RoomTypeDto updateRoomType(String roomTypeId, NewRoomTypeRequest newRoomTypeRequest) {
        RoomTypeEntity roomTypeEntity = roomTypeRepository.findById(roomTypeId)
                .orElseThrow(() -> new RoomTypeNotFoundException(roomTypeId));

        roomTypeEntity.setTitle(newRoomTypeRequest.getTitle());
        roomTypeEntity.setCapacity(newRoomTypeRequest.getCapacity());

        RoomTypeEntity savedRoomType = roomTypeRepository.save(roomTypeEntity);
        return RoomTypeDto.from(savedRoomType);
    }

    public void deleteRoomType(String roomTypeId) {
        roomTypeRepository.deleteById(roomTypeId);
    }
}
