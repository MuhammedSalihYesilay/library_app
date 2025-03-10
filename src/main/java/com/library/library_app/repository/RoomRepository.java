package com.library.library_app.repository;

import com.library.library_app.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, String> {
    Optional<RoomEntity> findById(String roomId);
}
