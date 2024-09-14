package com.example.gastar;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gastar.product.entity.Product;
import com.example.gastar.product.service.ProductService;
import com.example.gastar.product.ui.ProductList;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final ProductService productService = new ProductService();

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

        this.setProductComponent();
    }

    private void setProductComponent() {
        List<Product> products = this.productService.get();
        ListView listView = findViewById(R.id.productList);
        listView.setAdapter(new ProductList(MainActivity.this, products));
    }
}