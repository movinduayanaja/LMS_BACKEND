package com.lib1.backendlib.controller;

import com.lib1.backendlib.dto.category.*;
import com.lib1.backendlib.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService service;
    public CategoryController(CategoryService service) { this.service = service; }

    @PostMapping
    @PreAuthorize("hasRole('LIBRARIAN')")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse create(@Valid @RequestBody CategoryCreateRequest req) {
        return service.create(req);
    }

    @GetMapping("/{id}")
    public CategoryResponse get(@PathVariable Integer id) {
        return service.getById(id);
    }

    @GetMapping
    public List<CategoryResponse> getAll() {
        return service.getAll();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public CategoryResponse update(@PathVariable Integer id,
                                   @Valid @RequestBody CategoryUpdateRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('LIBRARIAN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}