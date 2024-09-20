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

import com.example.gastar.R;
import com.example.gastar.person.entity.Person;
import com.example.gastar.person.service.PersonService;
import com.example.gastar.person.ui.PersonList;


import java.util.List;

public class PersonController extends Fragment {
    private final PersonService personService = new PersonService();

    public PersonController(){super(R.layout.product_card);}


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        ImageButton addButton = view.findViewById(R.id.handleAddProduct);
        addButton.setOnClickListener(v -> this.showAddDialog());

        this.setPersonComponent();
    }

    private void setPersonComponent() {
        List<Person> persons = this.personService.get();
        RecyclerView recyclerView = getView().findViewById(R.id.productList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        PersonList personList = new PersonList(persons);
        TextView totalPrice = getView().findViewById(R.id.totalPriceTextView);
        totalPrice.setText("Personas"); // todo: move to variable;
        recyclerView.setAdapter(personList);
    }

    public PersonService getPersonService(){
        return personService;
    }


    public void showAddDialog() {
        Context context = this.getContext();
        final EditText input = new EditText(context);
        input.setHint("Nombre");

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 40, 50, 10);
        layout.addView(input);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Añadir persona")
                .setView(layout)
                .setPositiveButton("Añadir", (dialog, which) -> {
                    String enteredText = input.getText().toString().trim();
                    if (!enteredText.isEmpty()) {
                        Toast.makeText(context, "Añadido: " + enteredText, Toast.LENGTH_SHORT).show();
                        personService.add(new Person(enteredText));
                        this.setPersonComponent();
                    }
                })
                .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss()) // Dismiss dialog on cancel
                .show();
    }
}
