package com.example.gastar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gastar.person.PersonController;
import com.example.gastar.product.ProductController;

public class MainActivity extends AppCompatActivity {
    private final Handler handler = Handler.getInstance();

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

        Button button = findViewById(R.id.add_product_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddProductActivity.class);

                startActivity(intent);
            }
        });
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

}