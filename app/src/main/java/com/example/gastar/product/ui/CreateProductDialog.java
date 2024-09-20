package com.example.gastar.product.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import com.example.gastar.person.entity.Person;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.gastar.R;

import java.util.ArrayList;
import java.util.List;

public class CreateProductDialog extends DialogFragment {
    //todo: create an array of selected, and an array of to be selected.

    private DialogInterface.OnClickListener clickListener;
    private List<Person> persons;
    private List<Person> selected = new ArrayList<>();

    public CreateProductDialog(DialogInterface.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public CreateProductDialog(DialogInterface.OnClickListener clickListener, List<Person> persons) {
        this.clickListener = clickListener;
        this.persons = persons;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.product_add_dialog, null);

        if(persons != null){

            Spinner ownerSpinner = dialogView.findViewById(R.id.product_owner);
            ArrayAdapter<Person> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, persons);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            ownerSpinner.setAdapter(adapter);
            LinearLayout consumersContainer = dialogView.findViewById(R.id.consumers_container);
            for (Person p : persons){
                CheckBox checkBox = getCheckBox(p, builder);
                consumersContainer.addView(checkBox);
            }
        }
        builder.setView(dialogView)
                .setPositiveButton("Agregar", this.clickListener)
                .setNegativeButton("Cancelar", (dialog, id) -> {
                    // User cancels the dialog.
                });


        // Create the AlertDialog object and return it.
        return builder.create();
    }

    private @NonNull CheckBox getCheckBox(Person p, AlertDialog.Builder builder) {
        CheckBox checkBox = new CheckBox(builder.getContext());
        checkBox.setText(p.getName());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    Log.d("PERSON_SELECT", "checked: " + p.getName());
                    selected.add(p);
                }
                else{
                    Log.d("PERSON_SELECT", "unchecked: " +p.getName());
                    selected.remove(p);
                }
            }
        });
        return checkBox;
    }

    public List<Person> getSelected(){
        return selected;
    }

}
