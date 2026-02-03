package com.lib1.backendlib.controller;

import com.lib1.backendlib.dto.book.*;
import com.lib1.backendlib.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;


import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService service;
    public BookController(BookService service) { this.service = service; }

    @PostMapping
    @PreAuthorize("hasRole('LIBRARIAN')")
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponse create(@Valid @RequestBody BookCreateRequest req) {
        return service.create(req);
    }

    @GetMapping("/{id}")
    public BookResponse get(@PathVariable Integer id) {
        return service.getById(id);
    }

    @GetMapping
    public List<BookResponse> getAll() {
        return service.getAll();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public BookResponse update(@PathVariable Integer id,
                               @Valid @RequestBody BookUpdateRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('LIBRARIAN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
