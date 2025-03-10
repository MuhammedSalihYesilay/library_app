package com.library.library_app.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewReservationRequest {

    @NotBlank(message = "Room ID is required")
    private String roomId;

    @Positive(message = "Period number must be positive")
    private int periodNumber;

    @NotNull(message = "Reservation date is required")
    private LocalDate date;

    @NotEmpty(message = "At least one student is required")
    private Set<String> studentIds;
}
