package com.lib1.backendlib.controller;

import com.lib1.backendlib.dto.reservation.*;
import com.lib1.backendlib.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService service;
    public ReservationController(ReservationService service) { this.service = service; }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER','LIBRARIAN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponse create(@Valid @RequestBody ReservationCreateRequest req) {
        return service.create(req);
    }

    @GetMapping("/{id}")
    public ReservationResponse get(@PathVariable Integer id) {
        return service.getById(id);
    }

    @GetMapping
    public List<ReservationResponse> getAll() {
        return service.getAll();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','LIBRARIAN')")
    public ReservationResponse update(@PathVariable Integer id,
                                      @Valid @RequestBody ReservationUpdateRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','LIBRARIAN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}