package com.example.gastar.product.service;

import com.example.gastar.product.entity.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProductService {
    private final List<Product> products = new ArrayList<>();

    public ProductService() {}

    public void add(Product product) {
        this.products.add(product);
    }

    public void del(UUID uuid) {
        this.products.removeIf((product -> product.getId() == uuid));
    }

    public Product get(UUID uuid) {
        Optional<Product> product = products.stream().filter((p -> p.getId().equals(uuid))).findFirst();
        if(!product.isPresent()) {
            throw new RuntimeException("can't find product xd 404");
        }
        return product.get();
    }

    public List<Product> get() {
        return this.products;
    }

}
