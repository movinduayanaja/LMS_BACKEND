package com.lib1.backendlib.service.impl;

import com.lib1.backendlib.domain.entity.Book;
import com.lib1.backendlib.domain.repository.BookRepository;
import com.lib1.backendlib.exception.NotFoundException;
import com.lib1.backendlib.service.BookImageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

@Service
public class BookImageServiceImpl implements BookImageService {

    private final BookRepository bookRepo;
    private final Path baseDir;
    private final String booksSubDir;

    public BookImageServiceImpl(BookRepository bookRepo,
                                @Value("${app.upload.dir}") String uploadDir,
                                @Value("${app.upload.books-subdir}") String booksSubDir) {
        this.bookRepo = bookRepo;
        this.baseDir = Path.of(uploadDir);
        this.booksSubDir = booksSubDir;
        try {
            Files.createDirectories(baseDir.resolve(booksSubDir));
        } catch (IOException e) {
            throw new RuntimeException("Failed to create upload directories", e);
        }
    }

    @Override
    public String uploadCover(Integer bookId, MultipartFile image) {
        if (image == null || image.isEmpty()) {
            throw new IllegalArgumentException("Image file is required");
        }
        String contentType = image.getContentType();
        if (contentType == null || (!contentType.equals("image/jpeg") && !contentType.equals("image/png"))) {
            throw new IllegalArgumentException("Only JPEG or PNG images are allowed");
        }

        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book not found: " + bookId));

        String ext = contentType.equals("image/png") ? ".png" : ".jpg";
        String safeName = "book_" + bookId + "_" + System.currentTimeMillis() + ext;

        Path target = baseDir.resolve(booksSubDir).resolve(safeName);
        try {
            Files.copy(image.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store image", e);
        }

        // Public URL (served by WebMvcConfig)
        String publicUrl = "/images/" + booksSubDir + "/" + safeName;
        book.setImageUrl(publicUrl);
        bookRepo.save(book);
        return publicUrl;
    }
}