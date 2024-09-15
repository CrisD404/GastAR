package com.example.gastar.product.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.gastar.R;
import com.example.gastar.product.entity.Product;

import java.util.List;

public class ProductList extends RecyclerView.Adapter<ProductList.ViewHolder> {
    private final List<Product> products;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameLabel;
        private final TextView priceLabel;

        public ViewHolder(View view) {
            super(view);
            nameLabel = view.findViewById(R.id.productName);
            priceLabel = view.findViewById(R.id.productPrice);
        }

        public TextView getNameLabel() {
            return nameLabel;
        }

        public TextView getPriceLabel() {
            return priceLabel;
        }
    }

    public ProductList(List<Product> products) {
        this.products = products;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.product_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getNameLabel().setText(products.get(position).getName());
        viewHolder.getPriceLabel().setText(products.get(position).getPrice().toString());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public Double getFullPrice() {
        return products.stream().mapToDouble(Product::getTotalPrice).sum();
    }
}
