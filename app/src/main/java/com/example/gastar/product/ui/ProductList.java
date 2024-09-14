package com.example.gastar.product.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gastar.R;
import com.example.gastar.product.entity.Product;

import java.util.List;

public class ProductList extends ArrayAdapter<Product> {
    private final Context context;
    private final List<Product> products;
    private final int resource;

    public ProductList(@NonNull Context context, @NonNull List<Product> products) {
        super(context, R.layout.product_item, products);
        this.resource = R.layout.product_item;
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(resource, null);
        }

        Product product = products.get(position);

        TextView nameLabel = view.findViewById(R.id.productName);
        TextView priceLabel = view.findViewById(R.id.productPrice);

        nameLabel.setText(product.getName());
        priceLabel.setText(product.getPrice().toString());

        return view;
    }
}
