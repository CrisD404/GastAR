package com.example.gastar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import com.example.gastar.person.AddPersonFragment;
import com.example.gastar.person.entity.Person;
import com.example.gastar.person.service.PersonService;
import com.example.gastar.product.AddProductFragment;
import com.example.gastar.product.entity.Product;
import com.example.gastar.product.exception.RequiredFieldException;
import com.example.gastar.product.service.ProductService;

import java.util.List;

public class AddProductActivity extends AppCompatActivity {
    private final PersonService personService = Handler.getInstance().getPersonService();
    private final ProductService productService = Handler.getInstance().getProductService();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_product);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button addProductButton = findViewById(R.id.add_product_new);
        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createProduct();
            }
        });
    }

    public void createProduct(){

        AddProductFragment addProductFragment =  (AddProductFragment) getSupportFragmentManager().findFragmentById(R.id.product_fields);
        AddPersonFragment addPersonFragment = (AddPersonFragment) getSupportFragmentManager().findFragmentById(R.id.person_fields);
        double price;

        String productName = addProductFragment.getNameInput();
        Person contributor = addPersonFragment.getContributor();
        List<Person> consumers = addPersonFragment.getConsumers();
        try{
            price = addProductFragment.getInputPrice();
            if (consumers.isEmpty()){
                throw new RequiredFieldException("Debes marcar al menos un comensal");
            }
        }
        catch (RequiredFieldException ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }

        personService.calculateSpending(consumers,price);
        personService.addContribution(contributor,price);
        Product product = new Product(productName,1,"morfi",price);
        productService.add(product);
        finish();
    }


}