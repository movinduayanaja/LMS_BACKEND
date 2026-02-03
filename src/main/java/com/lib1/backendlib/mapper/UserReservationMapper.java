package com.lib1.backendlib.mapper;

import com.lib1.backendlib.domain.entity.Reservation;
import com.lib1.backendlib.domain.entity.User;
import com.lib1.backendlib.dto.user.UserResponse;
import com.lib1.backendlib.dto.reservation.ReservationResponse;

public final class UserReservationMapper {

    private UserReservationMapper() {}

    public static UserResponse toUserResponse(User u) {
        return new UserResponse(
            u.getId(), u.getEmail(), u.getRole(), u.isBlacklisted(), u.getCreatedAt()
        );
    }

    public static ReservationResponse toReservationResponse(Reservation r) {
        return new ReservationResponse(
            r.getId(),
            r.getUser() != null ? r.getUser().getId() : null,
            r.getUser() != null ? r.getUser().getEmail() : null,
            r.getBook() != null ? r.getBook().getId() : null,
            r.getBook() != null ? r.getBook().getTitle() : null,
            r.getReservationDate(),
            r.getDueDate(),
            r.getStatus()
        );
    }
}