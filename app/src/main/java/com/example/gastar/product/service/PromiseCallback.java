package com.example.gastar.product.service;

import org.json.JSONObject;

public interface PromiseCallback {
    void onSuccess(JSONObject jsonObject);
    void onFailure(Exception e);
}
