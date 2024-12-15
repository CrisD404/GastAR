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

import com.example.gastar.login.entity.User;
import com.example.gastar.person.entity.Person;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;

import com.example.gastar.person.PersonController;
import com.example.gastar.product.ProductController;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class MainActivity extends AppCompatActivity {
    private final Handler handler = Handler.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseApp.initializeApp(this);
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

        Serializable serializableExtra = getIntent().getSerializableExtra("fullName");
        if (serializableExtra != null) {
            String fullName = serializableExtra.toString();
            Person person = new Person(fullName);
            this.handler.getPersonService().add(person);
        }
        else {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference usersRef = db.collection("users");
            usersRef.whereEqualTo("uid", currentUser.getUid()).get().addOnCompleteListener(task->{
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        User userData = document.toObject(User.class);
                        String fullName = userData.getName() + " " + userData.getLastName();
                        Person person = new Person(fullName);
                        this.handler.getPersonService().add(person);
                    }
                }
                else{
                    Log.e("ERR_FIREBASE_USER", task.getException().getMessage());
                }
            });

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        update();
    }

    public void update() {
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