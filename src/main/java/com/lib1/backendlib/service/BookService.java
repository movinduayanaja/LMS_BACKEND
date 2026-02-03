package com.lib1.backendlib.service;


import com.lib1.backendlib.dto.book.BookCreateRequest;
import com.lib1.backendlib.dto.book.BookResponse;
import com.lib1.backendlib.dto.book.BookUpdateRequest;

import java.util.List;

public interface BookService {
    BookResponse create(BookCreateRequest req);
    BookResponse getById(Integer id);
    List<BookResponse> getAll();        // could later switch to Pageable
    BookResponse update(Integer id, BookUpdateRequest req);
    void delete(Integer id);
}
