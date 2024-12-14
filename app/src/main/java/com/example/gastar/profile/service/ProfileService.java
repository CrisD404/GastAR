package com.example.gastar.profile.service;

import android.util.Log;

import com.example.gastar.login.entity.User;
import com.example.gastar.profile.dto.UserProfileDto;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ProfileService {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseAuth user = FirebaseAuth.getInstance();

    public CompletableFuture<UserProfileDto> getProfile() {
        CompletableFuture<UserProfileDto> future = new CompletableFuture<>();
        CollectionReference usersRef = this.db.collection("users");

        usersRef.whereEqualTo("uid", this.user.getUid()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Log.d("ProfileController", "Document data: " + document.getData());
                    Log.d("ProfileController", document.toObject(UserProfileDto.class).toString());
                    future.complete(document.toObject(UserProfileDto.class));
                }
            } else {
                Log.d("ProfileController", "Error getting documents: ", task.getException());
                future.completeExceptionally(new Exception("Error getting documents"));
            }
        });
        return future;
    }

    public CompletableFuture<Void> updateProfile(UserProfileDto userProfileDto) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        CollectionReference usersRef = this.db.collection("users");
        Map<String, Object> updates = new HashMap<>();

        if (!userProfileDto.getName().isEmpty()) {
            updates.put("name", userProfileDto.getName());
        }

        if (!userProfileDto.getLastName().isEmpty()) {
            updates.put("lastName", userProfileDto.getLastName());
        }

        if (!userProfileDto.getImgUrl().isEmpty()) {
            updates.put("imgUrl", userProfileDto.getImgUrl());
        }

        usersRef.whereEqualTo("uid", this.user.getUid()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    document.getReference().update(updates).addOnCompleteListener(updateTask -> {
                        if (updateTask.isSuccessful()) {

                            Log.d("ProfileController", "Document successfully updated!");
                            future.complete(null);
                        } else {
                            Log.d("ProfileController", "Error updating document: ", updateTask.getException());
                            future.completeExceptionally(new Exception("Error updating document"));
                        }
                    });
                }
            } else {
                Log.d("ProfileController", "Error getting documents: ", task.getException());
                future.completeExceptionally(new Exception("Error getting documents"));
            }
        });

        return future;
    }


}
