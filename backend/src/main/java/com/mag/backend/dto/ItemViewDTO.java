package com.mag.backend.dto;

import com.mag.backend.entity.Item;

public class ItemViewDTO {
    private final String title;
    private final String text;
    private final double price;
    private final long categoryId;

    private ItemViewDTO(String title, String text, double price, long categoryId) {
        this.title = title;
        this.text = text;
        this.price = price;
        this.categoryId = categoryId;
    }

    public static ItemViewDTO of(Item item) {
        return new ItemViewDTO(item.getTitle(), item.getText(), item.getPrice(), item.getCategory().getId());
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public double getPrice() {
        return price;
    }

    public long getCategoryId() {
        return categoryId;
    }
}
