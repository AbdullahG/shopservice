package com.mag.backend.dto;

import com.mag.backend.entity.Category;

public class CategoryViewDTO {
    private final long id;
    private final String title;
    private final int itemCount;

    private CategoryViewDTO(long id, String title, int itemCount) {
        this.id = id;
        this.title = title;
        this.itemCount = itemCount;
    }

    public static CategoryViewDTO of(Category category) {
        return new CategoryViewDTO(
                category.getId(),
                category.getTitle(),
                category.getItems().size()
        );
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getItemCount() {
        return itemCount;
    }
}
