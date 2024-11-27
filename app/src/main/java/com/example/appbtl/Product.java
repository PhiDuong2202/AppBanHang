package com.example.appbtl;

public class Product {
    private int id;
    private String name;
    private double price;
    private String description;
    private int categoryId;

    // Constructor với id
    public Product(int id, String name, double price, String description, int categoryId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.categoryId = categoryId;
    }

    // Constructor không có id (dùng khi thêm mới sản phẩm)
    public Product(String name, double price, String description, int categoryId) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.categoryId = categoryId;
    }

    // Các getter và setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}

