package com.lib1.backendlib.mapper;

import com.lib1.backendlib.domain.entity.Book;
import com.lib1.backendlib.domain.entity.Category;
import com.lib1.backendlib.dto.book.BookResponse;
import com.lib1.backendlib.dto.category.CategoryResponse;

public final class BookCategoryMapper {

    private BookCategoryMapper() {}

    public static CategoryResponse toCategoryResponse(Category c) {
        return new CategoryResponse(c.getId(), c.getName());
    }

    public static BookResponse toBookResponse(Book b) {
        Integer catId = b.getCategory() != null ? b.getCategory().getId() : null;
        String catName = b.getCategory() != null ? b.getCategory().getName() : null;
        return new BookResponse(
            b.getId(), b.getTitle(), b.getAuthor(), b.getGenre(),
            b.getLanguage(), b.getIsbn(), b.getStatus(), b.getImageUrl(),
            catId, catName
        );
    }
}