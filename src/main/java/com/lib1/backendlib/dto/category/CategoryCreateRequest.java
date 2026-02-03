package com.lib1.backendlib.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoryCreateRequest {
    @NotBlank
    @Size(max = 100)
    private String name;

    // getters/setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
