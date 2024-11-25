package com.example.gastar.product;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.gastar.R;
import com.example.gastar.product.exception.RequiredFieldException;
import com.example.gastar.product.service.Currency;
import com.example.gastar.product.service.PromiseCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class AddProductFragment extends Fragment {
    private JSONObject currencies;
    private final ArrayList<String> currencyNames = new ArrayList<>();
    public AddProductFragment(){
        super(R.layout.add_product_product_fields);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        Spinner currencySelector = getView().findViewById(R.id.currency_selector);
        Currency currency = new Currency();
        currency.fetchJSONObjectAsync(Currency.CURRENCY_ARS_URL, new PromiseCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    currencies = jsonObject.getJSONObject("ars");
                } catch (JSONException e) {
                    Log.e("JSON_ERR",e.toString());
                }
                Iterator<String> keys = currencies.keys();
                while (keys.hasNext()){
                    String n = keys.next();
                    currencyNames.add(n.toUpperCase());
                }
                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, currencyNames);
                requireActivity().runOnUiThread(()->{
                    ArrayAdapter<String> newSpinnerAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, currencyNames);
                    newSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    currencySelector.setAdapter(newSpinnerAdapter);

                    // Post the selection to the main thread's message queue
                    currencySelector.post(() -> currencySelector.setSelection(18));
                });
            }

            @Override
            public void onFailure(Exception e) {
                Log.e("API_FETCH_ERROR", e.toString());
            }
        });

    }

    public double getInputPrice() throws RequiredFieldException {
        //todo: make calculations related to currency
        Spinner currencySelector = getView().findViewById(R.id.currency_selector);
        double conversionFactor = 1;
        String selectedCurr = (String) currencySelector.getSelectedItem();
        if(currencies == null){
            throw new RequiredFieldException("Ocurrió un error transformando las monedas");
        }
        try {
            conversionFactor = currencies.getDouble(selectedCurr.toLowerCase());
        } catch (JSONException e) {
            throw new RequiredFieldException("Ocurrió un error transformando las monedas");
        }

        EditText numberInput = getView().findViewById(R.id.price_input);
        String numberStr = numberInput.getText().toString();
        if(numberStr.isEmpty()){
            throw new RequiredFieldException("Ingresar precio");
        }

        return Double.parseDouble(numberStr) * (1/conversionFactor);
    }

    public String getNameInput(){
        EditText nameInput = getView().findViewById(R.id.product_name_field);
        return nameInput.getText().toString();
    }

}
