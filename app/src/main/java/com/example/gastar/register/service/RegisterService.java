package com.example.gastar.register.service;

import android.util.Log;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.CompletableFuture;

public class RegisterService {

    private final FirebaseAuth mAuth;

    public RegisterService(){
        mAuth = FirebaseAuth.getInstance();
    }

    public CompletableFuture<FirebaseUser> register(String email, String password){
        CompletableFuture<FirebaseUser> future = new CompletableFuture<>();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task ->  {

                        if (!task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.w("RegisterService", "createUserWithEmail:failure", task.getException());
                            future.completeExceptionally(task.getException());
                        }

                        FirebaseUser user = mAuth.getCurrentUser();
                        Log.d("RegisterService", "createUserWithEmail:success");
                        future.complete(user);
                });
        return future;
    }

}
