package com.lib1.backendlib.service.impl;

import com.lib1.backendlib.domain.entity.Category;
import com.lib1.backendlib.domain.repository.CategoryRepository;
import com.lib1.backendlib.dto.category.*;
import com.lib1.backendlib.exception.NotFoundException;
import com.lib1.backendlib.mapper.BookCategoryMapper;
import com.lib1.backendlib.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repo;

    public CategoryServiceImpl(CategoryRepository repo) {
        this.repo = repo;
    }

    @Override
    public CategoryResponse create(CategoryCreateRequest req) {
        if (repo.existsByNameIgnoreCase(req.getName())) {
            throw new IllegalArgumentException("Category name already exists");
        }
        Category c = new Category();
        c.setName(req.getName().trim());
        return BookCategoryMapper.toCategoryResponse(repo.save(c));
    }

    @Override @Transactional(readOnly = true)
    public CategoryResponse getById(Integer id) {
        Category c = repo.findById(id).orElseThrow(() ->
            new NotFoundException("Category not found: " + id));
        return BookCategoryMapper.toCategoryResponse(c);
    }

    @Override @Transactional(readOnly = true)
    public List<CategoryResponse> getAll() {
        return repo.findAll().stream()
                .map(BookCategoryMapper::toCategoryResponse)
                .toList();
    }

    @Override
    public CategoryResponse update(Integer id, CategoryUpdateRequest req) {
        Category c = repo.findById(id).orElseThrow(() ->
            new NotFoundException("Category not found: " + id));
        if (!c.getName().equalsIgnoreCase(req.getName())
                && repo.existsByNameIgnoreCase(req.getName())) {
            throw new IllegalArgumentException("Category name already exists");
        }
        c.setName(req.getName().trim());
        return BookCategoryMapper.toCategoryResponse(repo.save(c));
    }

    @Override
    public void delete(Integer id) {
        if (!repo.existsById(id)) {
            throw new NotFoundException("Category not found: " + id);
        }
        repo.deleteById(id);
    }
}
