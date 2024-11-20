package com.example.gastar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import com.example.gastar.person.PersonController;
import com.example.gastar.product.ProductController;

public class MainActivity extends AppCompatActivity {
    private final Handler handler = Handler.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            this.goTo(LoginActivity.class);
            return;
        }

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button addProductBtn = findViewById(R.id.add_product_button);
        Button logoutBtn = findViewById(R.id.logout_btn);

        logoutBtn.setOnClickListener(v -> this.logout());
        addProductBtn.setOnClickListener(v -> this.goTo(AddProductActivity.class));

    }

    @Override
    protected void onResume() {
        super.onResume();
        update();
    }

    private void update(){
        ProductController productController = (ProductController) getSupportFragmentManager().findFragmentById(R.id.products_fragment);
        PersonController personController = (PersonController) getSupportFragmentManager().findFragmentById(R.id.persons_fragment);
        productController.setProductComponent();
        personController.setPersonComponent();
    }

    private void logout() {
        this.handler.getLoginService().logout();
        this.goTo(LoginActivity.class);
    }

    private <T extends Activity> void goTo(@NonNull Class<T> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }

}