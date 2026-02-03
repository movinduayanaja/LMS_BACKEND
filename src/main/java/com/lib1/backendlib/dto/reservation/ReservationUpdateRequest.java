package com.lib1.backendlib.dto.reservation;

import com.lib1.backendlib.domain.enums.ReservationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationUpdateRequest {
    // You can update status and/or dueDate (e.g., extend)
    @NotNull
    private ReservationStatus status;

    private LocalDate dueDate; // optional; when provided, must be >= reservationDate
}