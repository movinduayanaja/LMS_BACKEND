package com.lib1.backendlib.service.impl;

import com.lib1.backendlib.domain.entity.Book;
import com.lib1.backendlib.domain.entity.Reservation;
import com.lib1.backendlib.domain.entity.User;
import com.lib1.backendlib.domain.enums.BookStatus;
import com.lib1.backendlib.domain.enums.ReservationStatus;
import com.lib1.backendlib.domain.repository.BookRepository;
import com.lib1.backendlib.domain.repository.UserRepository;
import com.lib1.backendlib.domain.repository.ReservationRepository;
import com.lib1.backendlib.dto.reservation.*;
import com.lib1.backendlib.exception.NotFoundException;
import com.lib1.backendlib.mapper.UserReservationMapper;
import com.lib1.backendlib.service.ReservationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepo;
    private final UserRepository userRepo;
    private final BookRepository bookRepo;

    public ReservationServiceImpl(ReservationRepository reservationRepo,
                                  UserRepository userRepo,
                                  BookRepository bookRepo) {
        this.reservationRepo = reservationRepo;
        this.userRepo = userRepo;
        this.bookRepo = bookRepo;
    }

    @Override
    public ReservationResponse create(ReservationCreateRequest req) {
        // Validate days (project expects 7/14/21)
        if (!(req.getRentalDays() == 7 || req.getRentalDays() == 14 || req.getRentalDays() == 21)) {
            throw new IllegalArgumentException("rentalDays must be one of 7, 14, or 21");
        }

        User user = userRepo.findById(req.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found: " + req.getUserId()));
        if (user.isBlacklisted()) {
            throw new IllegalStateException("User is blacklisted and cannot reserve books");
        }

        Book book = bookRepo.findById(req.getBookId())
                .orElseThrow(() -> new NotFoundException("Book not found: " + req.getBookId()));
        if (book.getStatus() != BookStatus.AVAILABLE) {
            throw new IllegalStateException("Book is not available for reservation");
        }

        Reservation r = new Reservation();
        r.setUser(user);
        r.setBook(book);
        r.setReservationDate(LocalDate.now());
        r.setDueDate(r.getReservationDate().plusDays(req.getRentalDays()));
        r.setStatus(ReservationStatus.ACTIVE);

        // change book status to RESERVED
        book.setStatus(BookStatus.RESERVED);
        bookRepo.save(book);

        return UserReservationMapper.toReservationResponse(reservationRepo.save(r));
    }

    @Override @Transactional(readOnly = true)
    public ReservationResponse getById(Integer id) {
        Reservation r = reservationRepo.findById(id).orElseThrow(() ->
            new NotFoundException("Reservation not found: " + id));
        return UserReservationMapper.toReservationResponse(r);
    }

    @Override @Transactional(readOnly = true)
    public java.util.List<ReservationResponse> getAll() {
        return reservationRepo.findAll().stream()
                .map(UserReservationMapper::toReservationResponse)
                .toList();
    }

    @Override
    public ReservationResponse update(Integer id, ReservationUpdateRequest req) {
        Reservation r = reservationRepo.findById(id).orElseThrow(() ->
            new NotFoundException("Reservation not found: " + id));

        // Update status
        ReservationStatus oldStatus = r.getStatus();
        r.setStatus(req.getStatus());

        // Optionally update due date (e.g., extend)
        if (req.getDueDate() != null) {
            if (req.getDueDate().isBefore(r.getReservationDate())) {
                throw new IllegalArgumentException("dueDate cannot be before reservationDate");
            }
            r.setDueDate(req.getDueDate());
        }

        // If reservation marked RETURNED now and previously ACTIVE, free the book
        if (oldStatus == ReservationStatus.ACTIVE && r.getStatus() == ReservationStatus.RETURNED) {
            Book b = r.getBook();
            b.setStatus(BookStatus.AVAILABLE);
            bookRepo.save(b);
        }

        return UserReservationMapper.toReservationResponse(reservationRepo.save(r));
    }

    @Override
    public void delete(Integer id) {
        Reservation r = reservationRepo.findById(id).orElseThrow(() ->
            new NotFoundException("Reservation not found: " + id));

        // If ACTIVE, free the book first
        if (r.getStatus() == ReservationStatus.ACTIVE) {
            Book b = r.getBook();
            b.setStatus(BookStatus.AVAILABLE);
            bookRepo.save(b);
        }

        reservationRepo.deleteById(id);
    }
}
