package com.lib1.backendlib.service.impl;


import com.lib1.backendlib.domain.entity.Book;
import com.lib1.backendlib.domain.entity.Category;
import com.lib1.backendlib.domain.enums.BookStatus;
import com.lib1.backendlib.domain.repository.BookRepository;
import com.lib1.backendlib.domain.repository.CategoryRepository;
import com.lib1.backendlib.dto.book.*;
import com.lib1.backendlib.exception.NotFoundException;
import com.lib1.backendlib.mapper.BookCategoryMapper;
import com.lib1.backendlib.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepo;
    private final CategoryRepository categoryRepo;

    public BookServiceImpl(BookRepository bookRepo, CategoryRepository categoryRepo) {
        this.bookRepo = bookRepo;
        this.categoryRepo = categoryRepo;
    }

    @Override
    public BookResponse create(BookCreateRequest req) {
        if (req.getIsbn() != null && bookRepo.findByIsbn(req.getIsbn()).isPresent()) {
            throw new IllegalArgumentException("ISBN already exists");
        }
        Book b = new Book();
        b.setTitle(req.getTitle().trim());
        b.setAuthor(req.getAuthor().trim());
        b.setGenre(req.getGenre());
        b.setLanguage(req.getLanguage());
        b.setIsbn(req.getIsbn());
        b.setImageUrl(req.getImageUrl());
        b.setStatus(BookStatus.AVAILABLE); // default per schema

        if (req.getCategoryId() != null) {
            Category c = categoryRepo.findById(req.getCategoryId())
                    .orElseThrow(() -> new NotFoundException("Category not found: " + req.getCategoryId()));
            b.setCategory(c);
        }

        return BookCategoryMapper.toBookResponse(bookRepo.save(b));
    }

    @Override @Transactional(readOnly = true)
    public BookResponse getById(Integer id) {
        Book b = bookRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found: " + id));
        return BookCategoryMapper.toBookResponse(b);
    }

    @Override @Transactional(readOnly = true)
    public java.util.List<BookResponse> getAll() {
        return bookRepo.findAll().stream()
                .map(BookCategoryMapper::toBookResponse)
                .toList();
    }

    @Override
    public BookResponse update(Integer id, BookUpdateRequest req) {
        Book b = bookRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found: " + id));

        // ISBN uniqueness check if changed
        if (req.getIsbn() != null && !req.getIsbn().equals(b.getIsbn())
                && bookRepo.findByIsbn(req.getIsbn()).isPresent()) {
            throw new IllegalArgumentException("ISBN already exists");
        }

        b.setTitle(req.getTitle().trim());
        b.setAuthor(req.getAuthor().trim());
        b.setGenre(req.getGenre());
        b.setLanguage(req.getLanguage());
        b.setIsbn(req.getIsbn());
        b.setImageUrl(req.getImageUrl());

        if (req.getCategoryId() != null) {
            Category c = categoryRepo.findById(req.getCategoryId())
                    .orElseThrow(() -> new NotFoundException("Category not found: " + req.getCategoryId()));
            b.setCategory(c);
        } else {
            b.setCategory(null);
        }

        return BookCategoryMapper.toBookResponse(bookRepo.save(b));
    }

    @Override
    public void delete(Integer id) {
        if (!bookRepo.existsById(id)) {
            throw new NotFoundException("Book not found: " + id);
        }
        bookRepo.deleteById(id);
    }
}