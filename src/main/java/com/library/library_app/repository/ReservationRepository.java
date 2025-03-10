package com.library.library_app.repository;

import com.library.library_app.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, String> {

    @Query("SELECT r FROM ReservationEntity r JOIN r.students s " +
            "WHERE s.id = :studentId AND r.date >= :date")
    List<ReservationEntity> findByStudentIdAndDateGreaterThanEqual(
            @Param("studentId") String studentId,
            @Param("date") LocalDate date);

    // Check if a room is already reserved for a specific period and date
    boolean existsByRoomIdAndPeriodNumberAndDate(String roomId, int periodNumber, LocalDate date);

    // Check if a student has a reservation for a specific date and period
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM ReservationEntity r " +
            "JOIN r.students s WHERE s.id = :studentId AND r.date = :date AND r.periodNumber = :periodNumber")
    boolean existsByStudentIdAndDateAndPeriodNumber(
            @Param("studentId") String studentId,
            @Param("date") LocalDate date,
            @Param("periodNumber") int periodNumber);

}


