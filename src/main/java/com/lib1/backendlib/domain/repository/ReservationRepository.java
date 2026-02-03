package com.lib1.backendlib.domain.repository;

import com.lib1.backendlib.domain.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findByUser_Id(Integer userId);
    List<Reservation> findByBook_Id(Integer bookId);
}