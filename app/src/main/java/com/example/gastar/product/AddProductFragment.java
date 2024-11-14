package com.example.gastar.product;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.gastar.Handler;
import com.example.gastar.R;
import com.example.gastar.person.entity.Person;
import com.example.gastar.product.service.ProductService;

import java.util.ArrayList;

public class AddProductFragment extends Fragment {

    public AddProductFragment(){
        super(R.layout.add_product_product_fields);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        Spinner currencySelector = getView().findViewById(R.id.currency_selector);
        //test
        ArrayList<String> currencies = new ArrayList<>();
        currencies.add("USD");
        currencies.add("ARS");
        currencies.add("EUR");
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, currencies);
        currencySelector.setAdapter(spinnerAdapter);
    }

    public double getInputPrice(){
        //todo: make calculations related to currency
        EditText numberInput = getView().findViewById(R.id.price_input);
        String numberStr = numberInput.getText().toString();
        return Double.parseDouble(numberStr);
    }

    public String getNameInput(){
        EditText nameInput = getView().findViewById(R.id.product_name_field);
        return nameInput.getText().toString();
    }

}
