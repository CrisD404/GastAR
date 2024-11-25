package com.example.gastar.person;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gastar.Handler;
import com.example.gastar.R;
import com.example.gastar.person.entity.Person;
import com.example.gastar.person.service.PersonService;
import com.example.gastar.person.ui.PersonList;


import java.util.List;

public class PersonController extends Fragment {
    private final PersonService personService = Handler.getInstance().getPersonService();

    public PersonController(){super(R.layout.product_card);}


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        this.setPersonComponent();
    }

    public void setPersonComponent() {
        List<Person> persons = this.personService.get();
        RecyclerView recyclerView = getView().findViewById(R.id.productList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        PersonList personList = new PersonList(persons);
        TextView totalPrice = getView().findViewById(R.id.totalPriceTextView);
        totalPrice.setText("Personas"); // todo: move to variable;
        TextView personHeader = getView().findViewById(R.id.entity_header);
        TextView balanceHeader = getView().findViewById(R.id.value_header);
        personHeader.setText("Nombre");
        balanceHeader.setText("Balance");
        recyclerView.setAdapter(personList);
    }

    public PersonService getPersonService(){
        return personService;
    }


}
