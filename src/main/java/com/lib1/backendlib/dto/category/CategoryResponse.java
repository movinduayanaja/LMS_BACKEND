package com.lib1.backendlib.dto.category;

public class CategoryResponse {
    private Integer id;
    private String name;

    public CategoryResponse() {}
    public CategoryResponse(Integer id, String name) {
        this.id = id; this.name = name;
    }
    public Integer getId() { return id; }
    public String getName() { return name; }
}
