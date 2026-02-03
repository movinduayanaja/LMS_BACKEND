package com.lib1.backendlib.dto.book;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookUpdateRequest {

    @NotBlank
    @Size(max = 255)
    private String title;

    @NotBlank
    @Size(max = 255)
    private String author;

    @Size(max = 100)
    private String genre;

    @Size(max = 50)
    private String language;

    @Size(max = 13)
    @Pattern(regexp = "^[0-9Xx-]*$")
    private String isbn;

    @Size(max = 512)
    private String imageUrl;

    private Integer categoryId;
}