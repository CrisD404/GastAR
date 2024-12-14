package com.example.gastar;

import com.example.gastar.login.service.LoginService;
import com.example.gastar.person.service.PersonService;
import com.example.gastar.product.service.ProductService;
import com.example.gastar.profile.service.ProfileService;
import com.example.gastar.register.service.RegisterService;

public class Handler {
    private final PersonService personService;
    private final ProductService productService;
    private final LoginService loginService;
    private final RegisterService registerService;
    private final ProfileService profileService;
    private static Handler instance;

    private Handler() {
        this.personService = new PersonService();
        this.productService = new ProductService();
        this.loginService = new LoginService();
        this.registerService = new RegisterService();
        this.profileService = new ProfileService();
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

    public RegisterService getRegisterService() {
        return this.registerService;
    }

    public ProfileService getProfileService() {
        return this.profileService;
    }

    public static void resetInstance() {
        instance = new Handler();
    }
}
