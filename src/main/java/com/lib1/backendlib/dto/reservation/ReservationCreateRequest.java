package com.lib1.backendlib.dto.reservation;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationCreateRequest {

    @NotNull
    private Integer userId;

    @NotNull
    private Integer bookId;

    // Allowed values: 7, 14, 21 per plan
    @NotNull @Positive
    private Integer rentalDays;
}