package com.example.gastar.product;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gastar.Handler;
import com.example.gastar.R;
import com.example.gastar.product.entity.Product;
import com.example.gastar.product.service.ProductService;
import com.example.gastar.product.ui.ProductList;

import java.util.List;
import java.util.Locale;

public class ProductController extends Fragment {
    private final ProductService productService = Handler.getInstance().getProductService();

    public ProductController() {
        super(R.layout.product_card);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        this.setProductComponent();
    }

    public void setProductComponent() {
        List<Product> products = this.productService.get();
        RecyclerView recyclerView = getView().findViewById(R.id.productList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ProductList productList = new ProductList(products);
        TextView totalPrice = getView().findViewById(R.id.totalPriceTextView);
        String formatPrice = String.format(new Locale("es", "AR"), "Total: $%.2f", productList.getFullPrice());
        TextView productHeader = getView().findViewById(R.id.entity_header);
        TextView priceHeader = getView().findViewById(R.id.value_header);
        productHeader.setText("Productos");
        priceHeader.setText("Precio");
        totalPrice.setText(formatPrice);
        recyclerView.setAdapter(productList);
    }

}
