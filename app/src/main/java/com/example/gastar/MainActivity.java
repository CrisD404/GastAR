package com.example.gastar;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gastar.product.entity.Product;
import com.example.gastar.product.service.ProductService;
import com.example.gastar.product.ui.CreateProductDialog;
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
        RecyclerView recyclerView = findViewById(R.id.productList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ProductList productList = new ProductList(products);
        TextView totalPrice = findViewById(R.id.totalPriceTextView);
        totalPrice.setText(productList.getFullPrice().toString());
        recyclerView.setAdapter(productList);

        Button addProduct = findViewById(R.id.handleAddProduct);
        addProduct.setOnClickListener(v -> {
            this.showAddProductModal();
        });
    }

    private void showAddProductModal() {
        CreateProductDialog createProductDialog = new CreateProductDialog((dialog, id)-> {
            EditText productNameField = ((AlertDialog) dialog).findViewById(R.id.productNameField);
            EditText productPriceField = ((AlertDialog) dialog).findViewById(R.id.productPriceField);

            String productName = productNameField.getText().toString();
            String productPrice = productPriceField.getText().toString();

            if(!productName.isEmpty() && !productPrice.isEmpty()) {
                Product product = new Product(productName, 1, "Morfi", Double.parseDouble(productPrice));
                this.productService.add(product);
                //TODO: REPLACE FOR ONLY UPDATE METHOD
                this.setProductComponent();
            }
        });
        createProductDialog.show(getSupportFragmentManager(), "PRODUCT_DIALOG");
    }

}