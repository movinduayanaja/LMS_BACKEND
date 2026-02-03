package com.lib1.backendlib.dto.reservation;

import com.lib1.backendlib.domain.enums.ReservationStatus;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponse {
    private Integer id;
    private Integer userId;
    private String userEmail;
    private Integer bookId;
    private String bookTitle;
    private LocalDate reservationDate;
    private LocalDate dueDate;
    private ReservationStatus status;
}