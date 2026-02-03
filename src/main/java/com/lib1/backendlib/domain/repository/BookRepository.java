package com.lib1.backendlib.domain.repository;

import com.lib1.backendlib.domain.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {
    Optional<Book> findByIsbn(String isbn);
    List<Book> findByCategory_Id(Integer categoryId);
}