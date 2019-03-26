package com.mag.backend.dto;

public class ItemCreateDTO {
    private String title;
    private String text;
    private double price;
    private long category;

    public ItemCreateDTO() {
    }

    public ItemCreateDTO(String title, String text, double price, long category) {
        this.title = title;
        this.text = text;
        this.price = price;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getCategory() {
        return category;
    }

    public void setCategory(long category) {
        this.category = category;
    }
}
