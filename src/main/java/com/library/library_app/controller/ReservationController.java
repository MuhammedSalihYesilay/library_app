package com.library.library_app.controller;


import com.library.library_app.dto.model.ReservationDto;
import com.library.library_app.dto.request.NewReservationRequest;
import com.library.library_app.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/room/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationDto> createReservation(
            @RequestBody NewReservationRequest newReservationRequest,
            @RequestParam String studentId,
            UriComponentsBuilder ucb
    ) {
        ReservationDto reservation = reservationService.createReservation(
                studentId, newReservationRequest, newReservationRequest.getRoomId());

        URI locationOfNewRoom = ucb
                .path("/api/room/reservations/{id}")
                .buildAndExpand(reservation.getId())
                .toUri();

        return ResponseEntity.created(locationOfNewRoom).body(reservation);
    }

    @GetMapping("/room/{roomId}/availability")
    public ResponseEntity<Map<String, Boolean>> getRoomAvailability(
            @PathVariable String roomId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        Map<String, Boolean> availabilityMap = reservationService.getRoomPeriodsReservationStatus(roomId, date);
        return ResponseEntity.ok(availabilityMap);
    }


    @GetMapping("/student/active/{studentId}")
    @ResponseStatus(HttpStatus.OK)
    public boolean hasActiveReservation(@RequestParam String studentId) {
        return reservationService.hasActiveReservation(studentId);
    }

    @GetMapping("/check-student-reservation")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Boolean> checkUserReservation(
            @RequestParam String studentId,
            @RequestParam LocalDate date,
            @RequestParam int periodNumber) {
        return reservationService.checkUserReservationStatus(studentId, date, periodNumber);
    }

    @DeleteMapping("/{reservationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelReservation(
            @PathVariable String reservationId,
            @RequestParam String studentId) {
        reservationService.cancelReservation(studentId, reservationId);
    }
}

