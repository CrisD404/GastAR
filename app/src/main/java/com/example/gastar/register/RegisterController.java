package com.example.gastar.register;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.gastar.Handler;
import com.example.gastar.LoginActivity;
import com.example.gastar.MainActivity;
import com.example.gastar.R;
import com.example.gastar.login.entity.User;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class RegisterController extends Fragment {

    private EditText email;
    private EditText name;
    private EditText surname;
    private EditText password;
    private ProgressBar loading;
    private final Handler handler = Handler.getInstance();

    public RegisterController() {
        super(R.layout.register_form);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        Button registerBtn = view.findViewById(R.id.register_btn);
        Button gotoLogin = view.findViewById(R.id.goto_login);

        this.email = view.findViewById(R.id.register_email_field);
        this.password = view.findViewById(R.id.register_password_field);
        this.name = view.findViewById(R.id.register_name_field);
        this.surname = view.findViewById(R.id.register_surname_field);
        this.loading = view.findViewById(R.id.progress_loader);

        registerBtn.setOnClickListener(v -> this.register());
        gotoLogin.setOnClickListener(v -> {
            Intent intent = new Intent(this.getContext(), LoginActivity.class);
            startActivity(intent);
        });
    }

    private void register() {
        Handler.resetInstance();
        String email = this.email.getText().toString();
        String password = this.password.getText().toString();
        String name = this.name.getText().toString();
        String surname = this.surname.getText().toString();


        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            this.showMessage("El email ingresado no es válido");
            return;
        }

        if (!password.matches("^(?=.*\\d).{5,}$")) {
            this.showMessage("La contraseña ingresada no es válida");
            return;
        }

        if (!name.isBlank()) {
            this.showMessage("Ingresa tu nombre");
            return;
        }

        if (!surname.isBlank()) {
            this.showMessage("Ingresa tu apellido");
            return;
        }


        this.loading.setVisibility(View.VISIBLE);
        this.handler.getRegisterService().register(email, password)
                .thenAccept(user -> {
                    String defaultImg = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png";
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    CollectionReference usersRef = db.collection("users");
                    Map<String, Object> userData = new HashMap<>();
                    userData.put("active", true);
                    userData.put("email", email);
                    userData.put("imgUrl", defaultImg);
                    userData.put("name", name);
                    userData.put("lastName", surname);
                    userData.put("uid", user.getUid());
                    usersRef.document(user.getUid()).set(userData).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Log.d("RegisterController", "Successfully created user data");
                            User userObj = new User(user.getUid(), name, surname, email, true);
                            Intent intent = new Intent(this.getContext(), MainActivity.class);
                            Log.d("RegisterController", "fullName: " + name + " " + surname);
                            intent.putExtra("fullName", name + " " + surname);
                            startActivity(intent);
                            this.loading.setVisibility(View.GONE);
                        } else {
                            Log.e("RegisterController", "Error creating documents: ", task.getException());
                            this.loading.setVisibility(View.GONE);
                        }
                    });

                })
                .exceptionally(err -> {
                    this.showMessage("Ocurrió un error, por favor intentalo más tarde.");
                    this.loading.setVisibility(View.GONE);
                    return null;
                });
    }

    private void showMessage(String message) {
        Toast.makeText(this.getContext(), message,
                Toast.LENGTH_SHORT).show();
    }


}
