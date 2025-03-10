package com.library.library_app.service;

import com.library.library_app.config.period.PeriodManager;
import com.library.library_app.dto.model.ReservationDto;
import com.library.library_app.dto.request.NewReservationRequest;
import com.library.library_app.entity.RoomEntity;
import com.library.library_app.entity.ReservationEntity;
import com.library.library_app.entity.StudentEntity;
import com.library.library_app.exception.*;
import com.library.library_app.repository.ReservationRepository;
import com.library.library_app.repository.RoomRepository;
import com.library.library_app.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


@Service
@RequiredArgsConstructor
public class ReservationService {

    private final PeriodManager periodManager;
    private final StudentRepository studentRepository;
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;

    @Transactional
    public ReservationDto createReservation(String studentId, NewReservationRequest request, String roomId) {
        validateStudentReservation(studentId, request.getDate(), request.getPeriodNumber());
        validateRoomAvailability(request, roomId);

        StudentEntity student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));

        RoomEntity roomEntity = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException(roomId));

        ReservationEntity reservation = createRoomReservation(studentId, request, roomEntity);

        // Öğrenciyi rezervasyona ekle
        Set<StudentEntity> students = new HashSet<>();
        students.add(student);
        reservation.setStudents(students);

        ReservationEntity savedReservation = reservationRepository.save(reservation);

        // Öğrencinin rezervasyon listesine ekle
        student.getReservations().add(savedReservation);

        return ReservationDto.from(savedReservation);
    }

    private void validateStudentReservation(String studentId, LocalDate date, int periodNumber) {
        LocalDate reservationDate = (date != null) ? date : LocalDate.now();

        if (hasActiveReservation(studentId)) {
            throw new UserHasActiveReservationException(studentId);
        }

        // Aynı gün ve periyot için rezervasyon kontrolü
        if (reservationRepository.existsByStudentIdAndDateAndPeriodNumber(studentId, reservationDate, periodNumber)) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime periodEndTime = periodManager.calculatePeriodEndTime(periodNumber, reservationDate);
            if (periodEndTime.isAfter(now)) {
                throw new UserHasActiveReservationException(studentId);
            }
        }

        if (!periodManager.isPeriodAvailableForReservation(periodNumber, reservationDate)) {
            throw new InvalidPeriodException();
        }
    }

    private void validateRoomAvailability(NewReservationRequest request, String roomId) {
        if (roomId == null) {
            throw new RoomNotFoundException(roomId);
        }

        LocalDate reservationDate = (request.getDate() != null) ? request.getDate() : LocalDate.now();

        // Periyot kontrolü - geçmiş tarih veya 2 gün sonrası kontrolü
        if (!periodManager.isPeriodAvailableForReservation(request.getPeriodNumber(), reservationDate)) {
            throw new InvalidPeriodException();
        }

        if (reservationRepository.existsByRoomIdAndPeriodNumberAndDate(
                roomId,
                request.getPeriodNumber(),
                reservationDate)) {
            throw new RoomAlreadyReserveTimeException();
        }
    }

    private ReservationEntity createRoomReservation(String studentId, NewReservationRequest request, RoomEntity room) {
        LocalDate reservationDate = (request.getDate() != null) ? request.getDate() : LocalDate.now();

        return ReservationEntity.builder()
                .periodNumber(request.getPeriodNumber())
                .date(reservationDate)
                .room(room)
                .students(new HashSet<>())
                .build();
    }

    @Transactional(readOnly = true)
    public Map<String, Boolean> getRoomPeriodsReservationStatus(String roomId, LocalDate date) {
        if (!roomRepository.existsById(roomId)) {
            throw new RoomNotFoundException(roomId);
        }

        LocalDate checkDate = (date != null) ? date : LocalDate.now();

        Map<String, Boolean> periodStatus = new LinkedHashMap<>();

        for (int periodNumber = 1; periodNumber <= 6; periodNumber++) {
            periodManager.validatePeriodNumber(periodNumber);

            boolean isRoomReserved = reservationRepository.existsByRoomIdAndPeriodNumberAndDate(
                    roomId, periodNumber, checkDate);

            String periodKey = periodNumber + ". Period  (true = reserved, false = available)";
            periodStatus.put(periodKey, isRoomReserved);
        }

        return periodStatus;
    }

    @Transactional(readOnly = true)
    public boolean hasActiveReservation(String studentId) {
        LocalDateTime now = LocalDateTime.now();
        LocalDate today = now.toLocalDate();

        List<ReservationEntity> studentReservations = reservationRepository
                .findByStudentIdAndDateGreaterThanEqual(studentId, today);

        for (ReservationEntity reservation : studentReservations) {
            LocalDateTime endTime = periodManager.calculatePeriodEndTime(
                    reservation.getPeriodNumber(), reservation.getDate());

            if (endTime.isAfter(now)) {
                return true;
            }
        }

        return false;
    }

    public Map<String, Boolean> checkUserReservationStatus(String studentId, LocalDate date, int periodNumber) {
        Map<String, Boolean> status = new HashMap<>();

        boolean hasActive = hasActiveReservation(studentId);
        boolean hasReservationInPeriod = reservationRepository
                .existsByStudentIdAndDateAndPeriodNumber(studentId, date, periodNumber);

        boolean isPeriodAvailable = periodManager.isPeriodAvailableForReservation(periodNumber, date);

        status.put("hasActiveReservation", hasActive);
        status.put("hasReservationInPeriod", hasReservationInPeriod);
        status.put("isPeriodAvailable", isPeriodAvailable);
        status.put("canMakeReservation", !hasActive && !hasReservationInPeriod && isPeriodAvailable);

        /*hasActiveReservation: Kullanıcının aktif rezervasyonu olup olmadığı.
        hasReservationInPeriod: Belirtilen tarihte ve periyotta rezervasyonu olup olmadığı.
        isPeriodAvailable: Periyodun rezervasyona uygun olup olmadığı.
        canMakeReservation: Eğer kullanıcı aktif rezervasyona sahip değilse, belirtilen tarihte ve*/

        return status;
    }

    @Transactional
    public void cancelReservation(String studentId, String reservationId) {
        ReservationEntity reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException(reservationId));

        StudentEntity student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));
        student.getReservations().remove(reservation);

        reservationRepository.delete(reservation);
    }
}




