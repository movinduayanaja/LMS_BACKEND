package com.lib1.backendlib.service;

import com.lib1.backendlib.dto.reservation.*;

import java.util.List;

public interface ReservationService {
    ReservationResponse create(ReservationCreateRequest req);
    ReservationResponse getById(Integer id);
    List<ReservationResponse> getAll();
    ReservationResponse update(Integer id, ReservationUpdateRequest req);
    void delete(Integer id); // cancel; if ACTIVE, free the book
}