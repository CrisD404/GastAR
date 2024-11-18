package com.example.gastar;

import com.example.gastar.person.service.PersonService;
import com.example.gastar.product.service.ProductService;

public class Handler {
    private final PersonService personService;
    private final ProductService productService;
    private static Handler instance;

    private Handler(){
        personService = new PersonService();
        productService = new ProductService();
    }

    public static Handler getInstance() {
        if(instance == null){
            instance = new Handler();
        }
        return instance;
    }

    public PersonService getPersonService() {
        return personService;
    }

    public ProductService getProductService() {
        return productService;
    }
}
