/*package com.library.library_app.repository;

import com.library.library_app.entity.RoomAvailabilityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomAvailabilityRepository extends JpaRepository<RoomAvailabilityEntity, String> {

    Optional<RoomAvailabilityEntity> findByRoomIdAndPeriodNumber(String roomId, int periodNumber);

    List<RoomAvailabilityEntity> findByRoomIdOrderByPeriodNumberAsc(String roomId);

    void deleteByRoomIdAndPeriodNumber(String roomId, int periodNumber);

}*/