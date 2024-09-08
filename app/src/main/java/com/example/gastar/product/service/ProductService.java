package com.example.gastar.product.service;

import com.example.gastar.product.entity.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProductService {
    private final List<Product> products = new ArrayList<>();

    public void add(Product product) {
        this.products.add(product);
    }

    public void del(UUID uuid) {
        this.products.removeIf((product -> product.getId() == uuid));
    }

    public Product get(UUID uuid) {
        return (Product) this.products.stream().filter(product -> product.getId() == uuid);
    }

    public List<Product> get() {
        return this.products;
    }

}
