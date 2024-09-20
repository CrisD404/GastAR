package com.example.gastar.product;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gastar.R;
import com.example.gastar.person.entity.Person;
import com.example.gastar.product.entity.Product;
import com.example.gastar.product.service.ProductService;
import com.example.gastar.product.ui.ConsumerAdapter;
import com.example.gastar.product.ui.CreateProductDialog;
import com.example.gastar.product.ui.ProductList;

import java.util.List;
import java.util.Locale;

public class ProductController extends Fragment {
    private final ProductService productService = new ProductService();
    private List<Person> personList;

    public ProductController() {
        super(R.layout.product_card);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        ImageButton addProduct = view.findViewById(R.id.handleAddProduct);
        addProduct.setOnClickListener(v -> this.showAddProductModal());

        this.setProductComponent();
    }

    public void setPersonList(List<Person> persons){
        personList = persons;
    }

    private void setProductComponent() {
        List<Product> products = this.productService.get();
        RecyclerView recyclerView = getView().findViewById(R.id.productList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ProductList productList = new ProductList(products);
        TextView totalPrice = getView().findViewById(R.id.totalPriceTextView);
        String formatPrice = String.format(new Locale("es", "AR"),"Total: $%.2f", productList.getFullPrice());
        totalPrice.setText(formatPrice);
        recyclerView.setAdapter(productList);
    }

    private void showAddProductModal() {
        CreateProductDialog createProductDialog = new CreateProductDialog((dialog, id)-> {
            EditText productNameField = ((AlertDialog) dialog).findViewById(R.id.productNameField);
            EditText productPriceField = ((AlertDialog) dialog).findViewById(R.id.productPriceField);
            RecyclerView consumersFields = ((AlertDialog) dialog).findViewById(R.id.consumers_container);
            Spinner contributorField = ((AlertDialog) dialog).findViewById(R.id.product_contributor);

            ConsumerAdapter consumerAdapter = (ConsumerAdapter) consumersFields.getAdapter();
            String productName = productNameField.getText().toString();
            String productPrice = productPriceField.getText().toString();
            if(!productName.isEmpty() && !productPrice.isEmpty()) {
                double doublePrice = Double.parseDouble(productPrice);

                Product product = new Product(productName, 1, "Morfi", doublePrice);
                this.productService.add(product);

                Person contributor = (Person) contributorField.getSelectedItem();
                contributor.addContribution(doublePrice);
                assert consumerAdapter != null;
                List<Person> consumers = consumerAdapter.getSelected();
                for (Person c : consumers){
                    for(Person p : personList){
                        if(c.getId().equals(p.getId())){
                            p.addSpending(doublePrice/ consumers.size());
                        }
                    }
                }

                //TODO: REPLACE FOR ONLY UPDATE METHOD // ALSO UPDATE PERSONS FRAGMENT
                this.setProductComponent();
            }
        },personList);
        createProductDialog.show(getParentFragmentManager(), "PRODUCT_DIALOG");
    }
}
