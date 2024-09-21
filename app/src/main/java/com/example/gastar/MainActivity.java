package com.example.gastar;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import com.example.gastar.person.PersonController;
import com.example.gastar.person.entity.Person;
import com.example.gastar.product.ProductController;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        FragmentManager fragmentManager = getSupportFragmentManager();
        PersonController personController = (PersonController) fragmentManager.findFragmentById(R.id.persons_fragment);
        ProductController productController = (ProductController) fragmentManager.findFragmentById(R.id.products_fragment);
        productController.setPersonList(personController.getPersonService().get());
    }
}