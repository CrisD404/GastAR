package com.example.gastar.product.entity;

import java.util.UUID;

public class Product {
    private final UUID id = UUID.randomUUID();
    private String name;
    private Integer quantity;
    //TODO: REPLACE TO CATEGORY TYPE ENUM
    private String category;
    private Double price;

    public Product(String name, Integer quantity, String category, Double price) {
        this.name = name;
        this.quantity = quantity;
        this.category = category;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTotalPrice() {
        return this.getPrice() * this.getQuantity();
    }
}
