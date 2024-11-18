package com.example.gastar.person;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.example.gastar.product.ui.ConsumerAdapter;

import java.util.List;

public class AddPersonFragment  extends Fragment {
    private final PersonService personService = Handler.getInstance().getPersonService();

    public AddPersonFragment(){
        super(R.layout.add_product_person_fields);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        getView().findViewById(R.id.add_person_button).setOnClickListener((v)->showAddDialog());
        setPersonComponent();
    }

    public void setPersonComponent(){
        View view = getView();
        List<Person> persons = this.personService.get();
        ConsumerAdapter consumerAdapter = new ConsumerAdapter(persons);
        RecyclerView recyclerView = view.findViewById(R.id.person_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(consumerAdapter);
        Spinner contributorField = view.findViewById(R.id.food_payer);
        ArrayAdapter<Person> spinnerAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, persons);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        contributorField.setAdapter(spinnerAdapter);
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

    public Person getContributor(){
        Spinner contributorField = getView().findViewById(R.id.food_payer);
        return (Person) contributorField.getSelectedItem();
    }

    public List<Person> getConsumers(){
        RecyclerView recyclerView = getView().findViewById(R.id.person_list);
        ConsumerAdapter consumerAdapter = (ConsumerAdapter) recyclerView.getAdapter();
        return consumerAdapter.getSelected();
    }

}
