package com.lib1.backendlib.dto.book;
import com.lib1.backendlib.domain.enums.BookStatus;

public class BookResponse {
    private Integer id;
    private String title;
    private String author;
    private String genre;
    private String language;
    private String isbn;
    private BookStatus status;
    private String imageUrl;
    private Integer categoryId;
    private String categoryName;

    // constructors/getters
    public BookResponse(Integer id, String title, String author, String genre, String language,
                        String isbn, BookStatus status, String imageUrl,
                        Integer categoryId, String categoryName) {
        this.id = id; this.title = title; this.author = author;
        this.genre = genre; this.language = language; this.isbn = isbn;
        this.status = status; this.imageUrl = imageUrl;
        this.categoryId = categoryId; this.categoryName = categoryName;
    }

    public Integer getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getGenre() { return genre; }
    public String getLanguage() { return language; }
    public String getIsbn() { return isbn; }
    public BookStatus getStatus() { return status; }
    public String getImageUrl() { return imageUrl; }
    public Integer getCategoryId() { return categoryId; }
    public String getCategoryName() { return categoryName; }
}
