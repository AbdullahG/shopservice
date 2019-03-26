package com.mag.backend.api;

import com.mag.backend.dto.CategoryCreateDTO;
import com.mag.backend.dto.CategoryViewDTO;
import com.mag.backend.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/category")
public class CategoryAPI {

    private final CategoryService categoryService;

    public CategoryAPI(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryViewDTO> categories() {
        return categoryService.list();
    }

    @PostMapping
    public CategoryViewDTO create(@RequestBody CategoryCreateDTO createDTO) {
        return categoryService.create(createDTO.getTitle());
    }
}
