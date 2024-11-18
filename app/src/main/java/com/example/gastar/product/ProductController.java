package com.example.gastar.product;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gastar.Handler;
import com.example.gastar.R;
import com.example.gastar.person.PersonController;
import com.example.gastar.person.entity.Person;
import com.example.gastar.product.entity.Product;
import com.example.gastar.product.exception.RequiredFieldException;
import com.example.gastar.product.service.ProductService;
import com.example.gastar.product.ui.ConsumerAdapter;
import com.example.gastar.product.ui.CreateProductDialog;
import com.example.gastar.product.ui.ProductList;

import java.util.List;
import java.util.Locale;

public class ProductController extends Fragment {
    private final ProductService productService = Handler.getInstance().getProductService();
    private PersonController personController;
    private List<Person> personList;

    public ProductController() {
        super(R.layout.product_card);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        ImageButton addProduct = view.findViewById(R.id.handleAddProduct);
        addProduct.setOnClickListener(v -> this.showAddProductModal());
        this.setProductComponent();
        personController = (PersonController) getParentFragmentManager().findFragmentById(R.id.persons_fragment);
        personList = personController.getPersonService().get();
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

    private void showAddProductModal() {
        if (personList.isEmpty()) {
            Toast.makeText(getContext(), "Debes agregar almenos una persona a la lista", Toast.LENGTH_SHORT).show();
            return;
        }

        CreateProductDialog createProductDialog = new CreateProductDialog((dialog, id) -> {
            EditText productNameField = ((AlertDialog) dialog).findViewById(R.id.productNameField);
            EditText productPriceField = ((AlertDialog) dialog).findViewById(R.id.productPriceField);
            RecyclerView consumersFields = ((AlertDialog) dialog).findViewById(R.id.consumers_container);
            Spinner contributorField = ((AlertDialog) dialog).findViewById(R.id.product_contributor);
            ConsumerAdapter consumerAdapter = (ConsumerAdapter) consumersFields.getAdapter();
            String productName = productNameField.getText().toString();
            String productPrice = productPriceField.getText().toString();
            Person person = (Person) contributorField.getSelectedItem();
            List<Person> consumers;
            try {
                if (productName.isEmpty()) {
                    throw new RequiredFieldException("El campo 'nombre' es requerido");
                }
                if (productPrice.isEmpty()) {
                    throw new RequiredFieldException("El campo 'precio' es requerido");
                }
                if (consumerAdapter == null) {
                    throw new RequiredFieldException("Error al inicializar la vista de personas");
                }
                consumers = consumerAdapter.getSelected();
                if(consumers.isEmpty()) {
                    throw new RequiredFieldException("Se debe seleccionar al menos un comensal");
                }
            } catch (RequiredFieldException ex) {
                Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }

            Double doublePrice = Double.parseDouble(productPrice);
            Product product = new Product(productName, 1, "Morfi", doublePrice);

            try {
                this.productService.add(product);
                if (personController != null) {
                    this.personController.getPersonService().calculateSpending(consumers, doublePrice);
                    this.personController.getPersonService().addContribution(person, doublePrice);
                    this.personController.setPersonComponent();
                }
            } catch (RuntimeException ex) {
                Log.w("addProduct", "showAddProductModal: ", ex);
            }

            this.setProductComponent();
        }, personList);
        createProductDialog.show(getParentFragmentManager(), "PRODUCT_DIALOG");
    }
}
