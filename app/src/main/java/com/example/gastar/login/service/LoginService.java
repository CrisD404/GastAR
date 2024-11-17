package com.example.gastar.login.service;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.CompletableFuture;


public class LoginService {

    private final FirebaseAuth mAuth;

    public LoginService() {
        this.mAuth = FirebaseAuth.getInstance();
    }

    public CompletableFuture<FirebaseUser> auth(String email, String password) {
        CompletableFuture<FirebaseUser> future = new CompletableFuture<>();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("LoginService", "signInWithEmail:failure", task.getException());
                        future.completeExceptionally(task.getException());
                    }

                    Log.d("LoginService", "signInWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();

                    if (user == null) {
                        future.completeExceptionally(new Exception("Error al consultar el usuario"));
                    }

                    future.complete(user);
                });
        return future;
    }
}
