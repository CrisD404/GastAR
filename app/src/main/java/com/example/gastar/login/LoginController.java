package com.example.gastar.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.gastar.Handler;
import com.example.gastar.MainActivity;
import com.example.gastar.R;
import com.example.gastar.login.service.LoginService;
import com.example.gastar.person.PersonController;

public class LoginController extends Fragment {

    private final Handler handler = Handler.getInstance();
    private EditText email;
    private EditText password;
    private ProgressBar loading;


    public LoginController() {
        super(R.layout.login_form);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        Button loginBtn = view.findViewById(R.id.login_btn);
        this.email = view.findViewById(R.id.login_email_field);
        this.password = view.findViewById(R.id.login_password_field);
        this.loading = view.findViewById(R.id.progress_loader);

        loginBtn.setOnClickListener(v -> this.login());
    }

    private void login() {
        String email = this.email.getText().toString();
        String password = this.password.getText().toString();

        //TODO: Set constants in resources
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            this.showMessage("El email ingresado no es válido");
            return;
        }

        if (!password.matches("^(?=.*\\d).{5,}$")) {
            this.showMessage("La contraseña ingresada no es válida");
            return;
        }

        this.loading.setVisibility(View.VISIBLE);
        this.handler.getLoginService().auth(email, password)
                .thenAccept(user -> {
                    Intent intent = new Intent(this.getContext(), MainActivity.class);
                    startActivity(intent);
                    this.loading.setVisibility(View.GONE);
                })
                .exceptionally(err -> {
                    this.showMessage("Las credenciales brindadas son incorrectas.");
                    this.loading.setVisibility(View.GONE);
                    return null;
                });
    }

    private void showMessage(String message) {
        Toast.makeText(this.getContext(), message,
                Toast.LENGTH_SHORT).show();
    }

}
