package com.example.gastar;

import com.example.gastar.login.service.LoginService;
import com.example.gastar.person.service.PersonService;
import com.example.gastar.product.service.ProductService;

public class Handler {
    private final PersonService personService;
    private final ProductService productService;
    private final LoginService loginService;
    private static Handler instance;

    private Handler() {
        this.personService = new PersonService();
        this.productService = new ProductService();
        this.loginService = new LoginService();
    }

    public static Handler getInstance() {
        if (instance == null) {
            instance = new Handler();
        }
        return instance;
    }

    public PersonService getPersonService() {
        return this.personService;
    }

    public ProductService getProductService() {
        return this.productService;
    }

    public LoginService getLoginService() {
        return this.loginService;
    }

    public static void resetInstance() {
        instance = new Handler();
    }
}
