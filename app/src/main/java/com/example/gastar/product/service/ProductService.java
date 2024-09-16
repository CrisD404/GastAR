package com.example.gastar.product.service;

import com.example.gastar.product.entity.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProductService {
    private final List<Product> products = new ArrayList<>();

    public ProductService() {
        Product p1 = new Product("Papas con cheddar", 1, "Morfi", 350.0);
        Product p2 = new Product("Pizza napo", 1, "Morfi", 1200.0);
        Product p3 = new Product("Empanada salteña", 1, "Morfi", 50.0);
        Product p4 = new Product("Birra honey", 3, "Chupi", 350.0);
        Product p5 = new Product("Coca 1l", 1, "Chupi", 1200.0);
        Product p6 = new Product("Juguito de naranja", 1, "Chupi", 50.0);
        products.add(p1);
        products.add(p2);
        products.add(p3);
        products.add(p4);
        products.add(p5);
        products.add(p6);
    }

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
