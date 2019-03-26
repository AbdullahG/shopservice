package com.mag.backend.service;

import com.google.common.base.Preconditions;
import com.mag.backend.dto.CategoryViewDTO;
import com.mag.backend.entity.Category;
import com.mag.backend.exception.ServiceException;
import com.mag.backend.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryViewDTO create(String title) {
        Preconditions.checkArgument(isNotEmpty(title), "Category title is not present");
        Preconditions.checkArgument(notExists(title), "Category title is already in use");
        Category category = categoryRepository.save(new Category(title));
        return CategoryViewDTO.of(category);
    }

    private boolean notExists(String title) {
        return !categoryRepository.existsByTitle(title);
    }

    @Transactional(readOnly = true)
    public List<CategoryViewDTO> list() {
        return categoryRepository.findAll().stream()
                .map(CategoryViewDTO::of)
                .collect(Collectors.toList());
    }

    Category getCategory(long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ServiceException(String.format("Category not found with ID #%d", categoryId)));
    }
}
