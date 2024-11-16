package com.example.gastar.product.service;

import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class Currency {
    public static final String CURRENCY_ARS_URL = "https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/currencies/ars.json";

    public void fetchJSONObjectAsync(String url, PromiseCallback callback) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .build();

                Response response = client.newCall(request).execute();
                if (response.isSuccessful() && response.body() != null) {
                    String jsonData = response.body().string();
                    JSONObject jsonObject = new JSONObject(jsonData);
                    callback.onSuccess(jsonObject);
                } else {
                    callback.onFailure(new Exception("Request failed: " + response.code()));
                }
            } catch (Exception e) {
                callback.onFailure(e);
            }
        });

        executor.shutdown();
    }
}

