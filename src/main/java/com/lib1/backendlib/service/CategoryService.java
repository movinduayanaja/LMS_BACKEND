package com.lib1.backendlib.service;


import com.lib1.backendlib.dto.category.CategoryCreateRequest;
import com.lib1.backendlib.dto.category.CategoryResponse;
import com.lib1.backendlib.dto.category.CategoryUpdateRequest;

import java.util.List;

public interface CategoryService {
    CategoryResponse create(CategoryCreateRequest req);
    CategoryResponse getById(Integer id);
    List<CategoryResponse> getAll();
    CategoryResponse update(Integer id, CategoryUpdateRequest req);
    void delete(Integer id);
}