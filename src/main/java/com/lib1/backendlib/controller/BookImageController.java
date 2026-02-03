package com.lib1.backendlib.controller;

import com.lib1.backendlib.service.BookImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

record ImageUploadResponse(String imageUrl) {}

@RestController
@RequestMapping("/api/books")
public class BookImageController {

    private final BookImageService imageService;

    public BookImageController(BookImageService imageService) {
        this.imageService = imageService;
    }

    @PreAuthorize("hasRole('LIBRARIAN')") // or hasAuthority("LIBRARIAN")
    @PostMapping(
        path = "/{id}/image",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE // makes the contract explicit
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ImageUploadResponse uploadCover(@PathVariable Integer id,
                                           @RequestParam("file") MultipartFile file) {
        String url = imageService.uploadCover(id, file);
        return new ImageUploadResponse(url);
    }
}